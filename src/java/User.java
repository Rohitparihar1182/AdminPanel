import java.sql.Connection;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class User {
    private String name;
    private String uid;
    private String branch;
    private boolean hostel;
    private boolean isUser;
    private String errMsg;
    public User(String name, String uid, String branch, boolean hostel){
        this.name = name;
        this.branch = branch;
        this.uid = uid;
        this.hostel = hostel;
        this.isUser = true;
        this.errMsg = null;
    }
    public User(String errMsg){
        this.isUser = false;
        this.errMsg = errMsg;
    }
    public String getName(){
        return this.name;
    }
    public String getUid() {
        return this.uid;
    }
    public String getBranch() {
        return this.branch;
    }
    public boolean isHostel() {
        return this.hostel;
    }
    public boolean isUser(){
        return this.isUser;
    }
    public String getErrMsg(){
        return this.errMsg;
    }
    public static User getUserBySessionId(Connection con, HttpSession session){
        try{
            String getQuery = "select * from student where sessionId = ?";
            PreparedStatement ps = con.prepareStatement(getQuery);
            ps.setString(1, session.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String uid = rs.getString("uid");
                String branch = rs.getString("branch");
                boolean hostel = rs.getBoolean("hostel");
                User user = new User(name, uid, branch, hostel);
                return user;
            }else{
                return new User("Cannot find user");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User("SQL Exception occured");
    }
}