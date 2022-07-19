package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

public class AdminPermission implements Serializable {
    private String id;
    private int permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public AdminPermission() {
    }

    public AdminPermission(String id, int permission) {
        this.id = id;
        this.permission = permission;
    }
}
