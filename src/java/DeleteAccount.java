import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author rohit
 */
@WebServlet(urlPatterns = {"/DeleteAccount"})
public class DeleteAccount extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String uid = request.getParameter("uid");
        RequestDispatcher successDispatcher = request.getRequestDispatcher("success.jsp");
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");
        if(session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
        }
        Connection con = Utils.getConnection();
        ResultSet rs = Utils.getAdminWithSession(session, con);
        try{
            if(rs.next()){
                boolean deleted = Utils.deleteUserAccount(uid, con);
                if(deleted){
                    request.setAttribute("message", "Account Deleted Successfully");
                    successDispatcher.forward(request, response);
                }else{
                    request.setAttribute("message", "500 Server error");
                    errorDispatcher.forward(request, response);
                }
            }else{
                request.setAttribute("message", "Some error occured");
                errorDispatcher.forward(request, response);
            }
        }catch(SQLException e){
            e.printStackTrace();
            request.setAttribute("message", "500 Server error");
            errorDispatcher.forward(request, response);
        }
    }
}
