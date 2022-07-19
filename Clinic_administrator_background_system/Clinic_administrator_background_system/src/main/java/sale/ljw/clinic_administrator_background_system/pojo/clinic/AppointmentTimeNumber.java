package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;

import java.io.Serializable;

public class AppointmentTimeNumber implements Serializable {
    private String id;
    private String timeNumber;
    private Doctor_information doctor;

    @Override
    public String toString() {
        return "AppointmentTimeNumber{" +
                "id='" + id + '\'' +
                ", timeNumber='" + timeNumber + '\'' +
                ", doctor=" + doctor +
                '}';
    }

    public AppointmentTimeNumber() {
    }

    public AppointmentTimeNumber(String id, String timeNumber, Doctor_information doctor) {
        this.id = id;
        this.timeNumber = timeNumber;
        this.doctor = doctor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeNumber() {
        return timeNumber;
    }

    public void setTimeNumber(String timeNumber) {
        this.timeNumber = timeNumber;
    }

    public Doctor_information getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor_information doctor) {
        this.doctor = doctor;
    }
}
