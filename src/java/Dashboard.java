import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = {"/dashboard"})
public class Dashboard extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        RequestDispatcher errorDispatcher = request.getRequestDispatcher("error.jsp");
        if(session == null) {
            request.setAttribute("message", "Session not found");
            errorDispatcher.forward(request, response);
            return;
        }

        try{
            Connection con = Utils.getConnection();
            ResultSet rs = Utils.getAdminWithSession(session, con);
            if(rs == null) {
                request.setAttribute("message", "Server error occured");
                errorDispatcher.forward(request, response);
            }
            if(rs.next()){
                request.setAttribute("adminName", rs.getString("name"));
                ResultSet allStudents = Utils.getAllStudents(con);
                if(allStudents != null ){
//                    List<Student> studentList = Utils.convertResultSetToList(allStudents);
//                    request.setAttribute("studentList", studentList);
                    
                    Map<String, List<Object>> result = Utils.convertResultSetToMap(allStudents);
                    for(Map.Entry<String, List<Object>> entry : result.entrySet()){
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    request.setAttribute("isSuccess", true);
                    
                }else{
                    request.setAttribute("isSuccess", false);
                }
                

                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
                dispatcher.forward(request, response);
            }else{
                request.setAttribute("message", "Invalid session");
                errorDispatcher.forward(request, response);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
