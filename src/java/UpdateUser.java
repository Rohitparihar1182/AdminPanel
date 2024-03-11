
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(urlPatterns = {"/UpdateUser"})
public class UpdateUser extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String uid = request.getParameter("uid");
        RequestDispatcher dispatcher = request.getRequestDispatcher("updateUser.jsp");
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");
        if (session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
            return;
        }

        Connection con = Utils.getConnection();
        ResultSet rs = Utils.getAdminWithSession(session, con);

        try {
            if (rs.next()) {
                String getQuery = "select * from student where uid = ?";
                PreparedStatement ps = con.prepareStatement(getQuery);
                ps.setString(1, uid);
                ResultSet user = ps.executeQuery();
                if (user.next()) {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("name", user.getString("name"));
                    userData.put("branch", user.getString("branch"));
                    userData.put("password", user.getString("password"));
                    userData.put("hostel", user.getBoolean("hostel"));
                    request.setAttribute("user-data", userData);
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("message", "Invalid session");
                errorDispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "500 server error");
            errorDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");

        if (session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
            return; 
        }

        try (Connection con = Utils.getConnection(); ResultSet rs = Utils.getAdminWithSession(session, con)) {

            if (rs.next()) {
                String name = Objects.requireNonNull(request.getParameter("name"));
                String branch = Objects.requireNonNull(request.getParameter("branch"));
                String uid = Objects.requireNonNull(request.getParameter("uid"));
                String password = Objects.requireNonNull(request.getParameter("password"));
                String confirmPassword = Objects.requireNonNull(request.getParameter("confirm-password"));

                if (name.isEmpty() || branch.isEmpty() || uid.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    request.setAttribute("message", "Cannot find required parameters.");
                    errorDispatcher.forward(request, response);
                    return;  
                }

                if (!password.equals(confirmPassword)) {
                    request.setAttribute("message", "Password should match confirm password");
                    errorDispatcher.forward(request, response);
                    return;  
                }

                String insertQuery = "UPDATE student SET name = ?, branch = ?, password = ?, hostel = ? where uid = ?";
                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                    ps.setString(1, name);
                    ps.setString(2, branch);
                    ps.setString(3, password);
                    ps.setBoolean(4, request.getParameter("hostel") != null);
                    ps.setString(5, uid.toLowerCase());

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        response.sendRedirect("/AdminPanel/dashboard");
                    } else {
                        request.setAttribute("message", "Failed to update account.");
                        errorDispatcher.forward(request, response);
                    }
                }

            } else {
                request.setAttribute("message", "Admin not found");
                errorDispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Server Error.");
            errorDispatcher.forward(request, response);
        }
    }
}
