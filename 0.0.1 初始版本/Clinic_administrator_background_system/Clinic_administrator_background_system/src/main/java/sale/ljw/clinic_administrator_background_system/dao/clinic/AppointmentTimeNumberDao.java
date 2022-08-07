package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTimeNumber;

import java.util.ArrayList;
@Mapper
@Repository
public interface AppointmentTimeNumberDao {
    public abstract ArrayList<AppointmentTimeNumber> findAll();
    public abstract Integer exitNumber(AppointmentTimeNumber appointmentTimeNumber);
    public abstract AppointmentTimeNumber findById(String id);
}
