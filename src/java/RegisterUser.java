
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.sql.*;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/RegisterUser"})
public class RegisterUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");
        if (session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
        }
        Connection con = Utils.getConnection();
        ResultSet rs = Utils.getAdminWithSession(session, con);

        try {
            if (rs.next()) {
                String name = request.getParameter("name");
                String branch = request.getParameter("branch");
                String uid = request.getParameter("uid");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirm-password");
                if (name == null || branch == null || uid == null || password == null || confirmPassword == null) {
                    response.sendRedirect("/AdminPanel/register.jsp");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    response.sendRedirect("/AdminPanel/register.jsp");
                    return;
                }

                String insertQuery = "INSERT INTO student(name, uid, branch, password) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, name);
                ps.setString(2, uid.toLowerCase());
                ps.setString(3, branch);
                ps.setString(4, password);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect("/AdminPanel/dashboard");
                } else {
                    request.setAttribute("message", "Failed to create account.");
                    errorDispatcher.forward(request, response);
                }
                
                con.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Server Error.");
            errorDispatcher.forward(request, response);
        }

    }
}
