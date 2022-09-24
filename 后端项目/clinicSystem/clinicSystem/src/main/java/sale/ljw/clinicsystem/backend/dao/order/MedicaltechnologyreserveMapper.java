package sale.ljw.clinicsystem.backend.dao.order;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindBindOrderIdMedicalListByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllHistoryMedicalTechnology;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.form.order.doctor.FindAllReserveMedicalTechnology;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnologyreserve;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【medicalTechnologyReserve】的数据库操作Mapper
* @createDate 2022-09-21 07:13:51
* @Entity sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnologyreserve
*/
@Mapper
@Repository
public interface MedicaltechnologyreserveMapper extends BaseMapper<Medicaltechnologyreserve> {
    public List<Map<String,Object>> findCurrentAppointmentMedicalTechnology(String doctorId,String status);
    public Map<String,Object> getAppointmentNumber(String doctorId);
    public List<Map<String,Object>> findAllReserveMedicalTechnology(FindAllReserveMedicalTechnology medicalTechnology, String doctorId);
    public List<Map<String,Object>> findHistoricalAppointments(FindAllReserveMedicalTechnology medicalTechnology, String doctorId);
    public Map<String,Object> findDiagnoseReportData(String id);
    public List<Map<String,Object>> queryBindingAppointment(String patientId);
    public Map<String,Object> confirmPayment(String id);

    Double getTotalPrice(String id);

    List<Map<String, Object>> findAllUnpaidOrder(FindMedicalTechnologyListByPatient medicalTechnology, String patientId);

    ArrayList<Map<String, Object>> findAllHistoryMedicalTechnology(FindAllHistoryMedicalTechnology historyMedical, String patientId);

    public Map<String,Object> findDiagnoseReportDataByPatient(String id,String patientId);

    List<Map<String, Object>> findBindOrderIdMedicalList(FindBindOrderIdMedicalListByDoctor medicalListByDoctor);

    List<Map<String, Object>> findHistoryBingOrderIdMedicalList(String patientId);
}




