
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.SecureRandom;

public class Utils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dev", "root", "123456");
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CreateSession createSession(Connection con, String id, String password, HttpServletRequest request) {
        try {
            String getQuery = "select * from admin where id = ?";
            PreparedStatement ps = con.prepareStatement(getQuery);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean match = rs.getString("password").equals(password);
                if (match) {
                    HttpSession prevSession = request.getSession(false);
                    if (prevSession != null) {
                        prevSession.invalidate();
                        String invalidateSessionQuery = "update admin set sessionId = ? where sessionId = ?";
                        PreparedStatement ps2 = con.prepareStatement(invalidateSessionQuery);
                        ps2.setString(1, "");
                        ps2.setString(2, prevSession.getId());
                        ps2.executeUpdate();
                    }

                    HttpSession newSession = request.getSession(true);
                    String newSessionId = newSession.getId();
                    String updateQuery = "UPDATE admin SET sessionId = ? WHERE id = ?";
                    PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                    updateStatement.setString(1, newSessionId);
                    updateStatement.setString(2, id);
                    int rowsAffected = updateStatement.executeUpdate();

                    // Check if the update was successful
                    if (rowsAffected > 0) {
                        return new CreateSession(newSession, "Session create successfully", true);
                    } else {
                        return new CreateSession(null, "Server Error", false);
                    }

                } else {
                    return new CreateSession(null, "Password did not match", false);
                }
            } else {
                return new CreateSession(null, "Id not found", false);
            }

        } catch (SQLException e) {
            System.out.println("Some error occured");
            e.printStackTrace();
        }
        return new CreateSession(null, "Some error occured", false);
    }

    public static ResultSet getAdminWithSession(HttpSession session, Connection con) {
        try {
            String getQuery = "select * from admin where sessionId = ?";
            PreparedStatement ps = con.prepareStatement(getQuery);
            ps.setString(1, session.getId());
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteUserAccount(String uid, Connection con) {
        try {
            String deleteQuery = "DELETE FROM student WHERE uid = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setString(1, uid);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean logout(HttpSession session, Connection con) {
        try {
            ResultSet rs = getAdminWithSession(session, con);
            if (rs == null) {
                session.invalidate();
                return true;
            }
            if (rs.next()) {
                String id = rs.getString("id");
                String updateQuery = "UPDATE admin SET sessionId = ? where id = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setString(1, null);
                updateStatement.setString(2, id);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    session.invalidate();
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Some error occured");
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet getAllStudents(Connection con) {
        try {
            String getQuery = "SELECT * FROM student";
            PreparedStatement ps = con.prepareStatement(getQuery);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, List<Object>> convertResultSetToMap(ResultSet resultSet) throws SQLException {
        Map<String, List<Object>> resultMap = new HashMap<>();

        // Get column names from ResultSet metadata
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            resultMap.put(columnName, new ArrayList<>());
        }

        while (resultSet.next()) {
            for (String columnName : resultMap.keySet()) {
                Object columnValue = resultSet.getObject(columnName);
                resultMap.get(columnName).add(columnValue);
            }
        }

        return resultMap;
    }

    public static int createGame(Connection con, String game_name) {
        String insertQuery = "INSERT INTO game (id, name) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, generateRandomString(10));
            ps.setString(2, game_name);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<String> getAllGames(Connection con) {
        List<String> gameNames = new ArrayList<>();
        String getQuery = "SELECT name FROM game";

        try (PreparedStatement ps = con.prepareStatement(getQuery); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                gameNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameNames;
    }

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public static Map<String, List<String>> getRegisteredStudentsForGames(Connection con) {
        Map<String, List<String>> gameRegistrations = new HashMap<>();

        String query = "SELECT g.name AS game_name, s.name AS student_name "
                + "FROM game g "
                + "LEFT JOIN student_games sg ON g.id = sg.game_id "
                + "LEFT JOIN student s ON sg.uid = s.uid";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String gameName = rs.getString("game_name");
                String studentName = rs.getString("student_name");

                // Initialize the list if it doesn't exist
                gameRegistrations.computeIfAbsent(gameName, k -> new ArrayList<>());

                // Add student name to the list if it's not null
                if (studentName != null) {
                    gameRegistrations.get(gameName).add(studentName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameRegistrations;
    }

}
