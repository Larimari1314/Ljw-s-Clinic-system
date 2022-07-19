package sale.ljw.clinic_administrator_background_system.pojo.clinic;

import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;

import java.io.Serializable;

public class ReservationInformation implements Serializable {
    private String id;
    private Patient_information patient;
    private Doctor_information doctor;
    private String appointment;

    public ReservationInformation(String id, Patient_information patient, Doctor_information doctor, String appointment) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
    }

    public ReservationInformation() {
    }

    @Override
    public String toString() {
        return "ReservationInformation{" +
                "id='" + id + '\'' +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", appointment='" + appointment + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient_information getPatient() {
        return patient;
    }

    public void setPatient(Patient_information patient) {
        this.patient = patient;
    }

    public Doctor_information getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor_information doctor) {
        this.doctor = doctor;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
}
