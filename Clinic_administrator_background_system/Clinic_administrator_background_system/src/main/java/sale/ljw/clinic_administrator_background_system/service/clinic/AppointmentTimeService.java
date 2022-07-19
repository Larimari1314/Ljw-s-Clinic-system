package sale.ljw.clinic_administrator_background_system.service.clinic;

import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTime;

import java.util.ArrayList;

public interface AppointmentTimeService {

    public abstract String findAllTime();

    public abstract String editAppointmentTime(AppointmentTime appointmentTime);

    public abstract String findTimeAfternoon(String did);
    public abstract String findTimeMorning(String did);
}
