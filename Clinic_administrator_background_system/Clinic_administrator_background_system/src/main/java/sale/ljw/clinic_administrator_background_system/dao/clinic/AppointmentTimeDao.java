package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTime;

import java.util.ArrayList;
@Mapper
@Repository
public interface AppointmentTimeDao {
    /**
     * 查找全部
     * @return
     */
    public abstract ArrayList<AppointmentTime> findAllTime();

    /**
     * 修改数据
     * @param appointmentTime
     * @return
     */
    public abstract Integer editAppointmentTime(AppointmentTime appointmentTime);

    /**
     * 查找上午时间
     * @return
     */
    public abstract AppointmentTime findTimeMorning();

    /**
     * 查找下午时间
     * @return
     */
    public abstract AppointmentTime findTimeAfternoon();
}
