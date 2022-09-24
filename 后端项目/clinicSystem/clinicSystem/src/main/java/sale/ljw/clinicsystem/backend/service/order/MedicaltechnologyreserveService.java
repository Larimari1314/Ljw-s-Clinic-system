package sale.ljw.clinicsystem.backend.service.order;

import cn.hutool.http.server.HttpServerRequest;
import sale.ljw.clinicsystem.backend.form.basic.doctor.EndOfVisitByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindBindOrderIdMedicalListByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllHistoryMedicalTechnology;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.form.basic.patient.MedicalTechnologyAppointmentForm;
import sale.ljw.clinicsystem.backend.form.order.doctor.FindAllReserveMedicalTechnology;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnologyreserve;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author 86155
* @description 针对表【medicalTechnologyReserve】的数据库操作Service
* @createDate 2022-09-21 07:13:51
*/
public interface MedicaltechnologyreserveService extends IService<Medicaltechnologyreserve> {

    String findCurrentAppointmentMedicalTechnology(HttpServletRequest req);

    String getAppointmentNumber(HttpServletRequest req);

    String findAllReserveMedicalTechnology(FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req);

    String judgmentStatusPaid(String id);

    String findDiagnoseReportData(String id,HttpServletRequest req);

    String cancelAppointment(String id,HttpServletRequest req);

    String findHistoricalAppointments(FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req);

    String queryBindingAppointment(HttpServletRequest req);

    String medicalTechnologyAppointment(MedicalTechnologyAppointmentForm medicalTechnology, HttpServletRequest req);

    String confirmPayment(String id);

    String payingEncryption(String id);

    String queryPaymentInformation(String payingEncryptionId);

    String getQRCodeMedical(String id);

    String scanPayStatusMedical(String id);

    String payingMedical(String id);

    String findAllUnpaidOrder(FindMedicalTechnologyListByPatient medicalTechnology, HttpServletRequest req);

    String refundMedicalOrder(String id, HttpServletRequest req);

    String endOfVisit(EndOfVisitByDoctor endOfVisitByDoctor, HttpServletRequest req);

    String viewReport(String id,HttpServletRequest req);


    String findAllHistoryMedicalTechnology(FindAllHistoryMedicalTechnology historyMedical, HttpServletRequest req);

    String viewTheReport(String id, HttpServletRequest req);

    String findBindOrderIdMedicalList(FindBindOrderIdMedicalListByDoctor medicalListByDoctor);

    String findHistoryBingOrderIdMedicalList(String patientId);
}
