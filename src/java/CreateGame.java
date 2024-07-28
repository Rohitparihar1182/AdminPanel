import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.sql.*;
import jakarta.servlet.http.*;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/CreateGame"})
public class CreateGame extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");

        if (session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
            return;
        }

        Connection con = null;
        ResultSet rs = null;
        
        try {
            con = Utils.getConnection();
            rs = Utils.getAdminWithSession(session, con);

            if (rs.next()) {
                String name = request.getParameter("name");
                if (name == null || name.trim().isEmpty()) {
                    response.sendRedirect("/AdminPanel/dashboard");
                    return;
                }

                int rowsAffected = Utils.createGame(con, name);
                if (rowsAffected > 0) {
                    response.sendRedirect("/AdminPanel/dashboard");
                } else {
                    request.setAttribute("message", "Failed to create game.");
                    errorDispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("message", "Invalid session.");
                errorDispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Server Error.");
            errorDispatcher.forward(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
