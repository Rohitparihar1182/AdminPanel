public class Student {
    private String name;
    private String uid;
    private String branch;
    private boolean hostel;
    public Student(String name, String uid, String branch, boolean hostel){
        this.name = name;
        this.uid = uid;
        this.branch = branch;
        this.hostel = hostel;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public boolean isHostel() {
        return hostel;
    }
    public void setHostel(boolean hostel) {
        this.hostel = hostel;
    }
    
}
