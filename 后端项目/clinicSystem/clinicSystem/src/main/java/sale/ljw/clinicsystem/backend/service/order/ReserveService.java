package sale.ljw.clinicsystem.backend.service.order;

import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【reserve】的数据库操作Service
* @createDate 2022-08-07 16:12:15
*/
public interface ReserveService extends IService<Reserve> {
    public abstract String findReserveNotViewed(FindReserveNotViewedByAdmin reserve);
    public abstract String cancelAppointment(DeleteIdsBYAdmin ids);
    public abstract String cancelAppointmentViews(FindReserveNotViewedByAdmin reserve);
    public abstract String resumeAppointment(DeleteIdsBYAdmin ids);
}
