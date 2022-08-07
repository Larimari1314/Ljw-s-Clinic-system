package sale.ljw.clinic_administrator_background_system.pojo.clinic;

public class AdminLogin {
    private String id;
    private String adminUserName;
    private String password;

    @Override
    public String toString() {
        return "AdminLogin{" +
                "id='" + id + '\'' +
                ", adminUserName='" + adminUserName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public AdminLogin(String id, String adminUserName, String password) {
        this.id = id;
        this.adminUserName = adminUserName;
        this.password = password;
    }

    public AdminLogin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
