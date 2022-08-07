package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

public class AccessControl implements Serializable {
    private int id;
    private String accessUrl;
    private boolean access;

    @Override
    public String toString() {
        return "AccessControl{" +
                "id=" + id +
                ", accessUrl='" + accessUrl + '\'' +
                ", access=" + access +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public AccessControl() {
    }

    public AccessControl(int id, String accessUrl, boolean access) {
        this.id = id;
        this.accessUrl = accessUrl;
        this.access = access;
    }
}
