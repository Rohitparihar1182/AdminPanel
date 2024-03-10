import jakarta.servlet.http.HttpSession;


public class CreateSession {
    final private HttpSession session;
    final private String status;
    final private boolean success;
    public CreateSession(HttpSession session, String status, boolean success){
        this.session = session;
        this.status = status;
        this.success = success;
    }
    public HttpSession getSeesion(){
        return this.session;
    }
    public String getStatus(){
        return this.status;
    }
    public boolean isSuccess(){
        return this.success;
    }
}
