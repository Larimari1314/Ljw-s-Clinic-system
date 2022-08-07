package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import java.io.Serializable;

public class AppointmentTime implements Serializable {
    private String id;
    private String startTime;
    private Integer step;
    private String endTime;

    public AppointmentTime() {
    }

    public AppointmentTime(String id, String startTime, Integer step, String endTime) {
        this.id = id;
        this.startTime = startTime;
        this.step = step;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AppointmentTime{" +
                "id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", step='" + step + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
