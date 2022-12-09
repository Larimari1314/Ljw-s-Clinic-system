package sale.ljw.clinicsystem.backend.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllCurrentAppointmentByPatient;
import sale.ljw.clinicsystem.backend.form.order.doctor.CheckOrderByDoctor;
import sale.ljw.clinicsystem.backend.form.order.doctor.ConfirmAppointmentByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindAppointmentTime;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindReserveByDoctor;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.form.order.patient.FindAllHistoryOrderFormByPatient;
import sale.ljw.clinicsystem.backend.form.order.patient.FindUnpaidOrderFormByPatient;
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
    ArrayList<Map<String, Object>> historyAppointment( FindReserveByDoctor reserve);

    Map<String,Object> checkOrderOnOrderFrom(CheckOrderByDoctor order);

    ArrayList<Map<String, Object>> checkOrderDrugOnOrderFrom(String id);
    ArrayList<Map<String, Object>> getAppointmentTime(FindAppointmentTime appointment);
    Map<String, Object> getDutyHours(String id);
    Map<String, Object> checkAppointmentTime(ConfirmAppointmentByDoctor confirmAppointment);
    ArrayList<Map<String, Object>> findCurrentAppointments(FindReserveByDoctor reserve);
    Map<String,Object> getAppointmentNumber(String doctorId);
    Map<String,Object> getPayAppointmentInformation(ConfirmAppointmentByDoctor confirmAppointment);
    ArrayList<Map<String, Object>> findAllHistoryOrderFormByPatient(FindAllHistoryOrderFormByPatient historyOrderForm);

    Map<String, Object> findOrderCompletion(String orderId);
    ArrayList<Map<String, Object>> findOrderCompletionDrugList(String orderId);

    ArrayList<Map<String, Object>> findUnpaidOrderForm(FindUnpaidOrderFormByPatient unpaidOrderForm);

    Map<String, Object> findOrderUnpaid(String orderId);

    ArrayList<Map<String, Object>> getAllCurrentAppointment(FindAllCurrentAppointmentByPatient currentAppointment);

    Integer getRemindersNumber(String patientId);

    Integer testingPatientsAppointments();
}




