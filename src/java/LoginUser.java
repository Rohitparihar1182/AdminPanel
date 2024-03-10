
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/LoginUser"})
public class LoginUser extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String password = request.getParameter("password");
        if(uid == null || password == null){
            response.sendRedirect("/login");
        }
        
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");

        Connection con = Utils.getConnection();
        CreateSession session = Utils.createSession(con, uid, password, request);
        if(session.isSuccess()){

            response.sendRedirect("/AdminPanel/dashboard");
        } else {
            request.setAttribute("message", session.getStatus());
            errorDispatcher.forward(request, response);
        }
          
    }
}
