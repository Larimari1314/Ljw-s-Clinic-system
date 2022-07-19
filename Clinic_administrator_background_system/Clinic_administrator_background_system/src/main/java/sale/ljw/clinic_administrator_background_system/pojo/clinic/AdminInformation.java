package sale.ljw.clinic_administrator_background_system.pojo.clinic;

public class AdminInformation {
    private String id;
    private String administratorPhone;
    private String avatarUrl;
    private String adminName;
    private int adminSex;
    private int adminAge;
    private String adminAddr;

    @Override
    public String toString() {
        return "AdminInformation{" +
                "id='" + id + '\'' +
                ", administratorPhone='" + administratorPhone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminSex=" + adminSex +
                ", adminAge=" + adminAge +
                ", adminAddr='" + adminAddr + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdministratorPhone() {
        return administratorPhone;
    }

    public void setAdministratorPhone(String administratorPhone) {
        this.administratorPhone = administratorPhone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getAdminSex() {
        return adminSex;
    }

    public void setAdminSex(int adminSex) {
        this.adminSex = adminSex;
    }

    public int getAdminAge() {
        return adminAge;
    }

    public void setAdminAge(int adminAge) {
        this.adminAge = adminAge;
    }

    public String getAdminAddr() {
        return adminAddr;
    }

    public void setAdminAddr(String adminAddr) {
        this.adminAddr = adminAddr;
    }

    public AdminInformation(String id, String administratorPhone, String avatarUrl, String adminName, int adminSex, int adminAge, String adminAddr) {
        this.id = id;
        this.administratorPhone = administratorPhone;
        this.avatarUrl = avatarUrl;
        this.adminName = adminName;
        this.adminSex = adminSex;
        this.adminAge = adminAge;
        this.adminAddr = adminAddr;
    }

    public AdminInformation() {
    }
}
