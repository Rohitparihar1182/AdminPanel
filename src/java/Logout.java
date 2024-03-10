import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.sql.*;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/Logout"})
public class Logout extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
        HttpSession session = request.getSession(false);
        Connection con = Utils.getConnection();
        boolean loggedOut = Utils.logout(session, con);
        if(loggedOut){
            RequestDispatcher successDispatcher = request.getRequestDispatcher("success.jsp");
            request.setAttribute("message", "Successfully Logged out");
            successDispatcher.forward(request, response);
        }else{
            RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");
            request.setAttribute("message", "Error logging out");
            errorDispatcher.forward(request, response);
        }
    }
}