package sale.ljw.clinic_administrator_background_system.service.clinic;

import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTimeNumber;

public interface AppointmentTimeNumberService {
    public abstract String findAll(Integer page);
    public abstract String exitNumber(String id,String text);
}
