package sale.ljw.clinicsystem.backend.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【reserve】的数据库操作Mapper
 * @createDate 2022-08-07 16:12:15
 * @Entity sale.ljw.clinicsystem.backend.pojo.order.Reserve
 */
@Mapper
@Repository
public interface ReserveMapper extends BaseMapper<Reserve> {
    ArrayList<Map<String, Object>> findByPatientIds(List<String> ids);

    ArrayList<Map<String, Object>> findByDoctorIds(List<String> ids);

    ArrayList<Map<String, Object>> findReserveNotViewed(FindReserveNotViewedByAdmin reserve);

    ArrayList<Map<String, Object>> cancelAppointmentViews(FindReserveNotViewedByAdmin reserve);

    Integer cancelAppointment(List<String> ids);
    Integer resumeAppointment(List<String> ids);
}




