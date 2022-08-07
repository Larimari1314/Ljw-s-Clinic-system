package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

public class AppointmentTimeWeb implements Serializable {
    private String time;
    private Boolean disable;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public AppointmentTimeWeb(String time, Boolean disable) {
        this.time = time;
        this.disable = disable;
    }

    public AppointmentTimeWeb() {
    }

    @Override
    public String toString() {
        return "AppointmentTimeWeb{" +
                "time='" + time + '\'' +
                ", disable=" + disable +
                '}';
    }
}
