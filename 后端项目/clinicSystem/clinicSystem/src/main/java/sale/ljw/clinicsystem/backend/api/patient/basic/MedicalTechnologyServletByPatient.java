package sale.ljw.clinicsystem.backend.api.patient.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindDoctorListByMedical;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.form.basic.patient.MedicalTechnologyAppointmentForm;
import sale.ljw.clinicsystem.backend.service.order.MedicalclassificationService;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyService;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyreserveService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/medicalTechnologyByPatient")
public class MedicalTechnologyServletByPatient {
    @Autowired
    private MedicaltechnologyService medicaltechnologyService;
    @Autowired
    private MedicalclassificationService medicalclassificationService;
    @Autowired
    private DoctorinformationService doctorinformationService;
    @Autowired
    private MedicaltechnologyreserveService medicaltechnologyreserveService;

    @ApiOperation(value = "获取基本检查列表")
    @GetMapping("/getMedicalProjectNameList")
    public String getMedicalProjectNameList() {
        return medicaltechnologyService.getMedicalProjectNameList();
    }

    @ApiOperation(value = "获取医技列表")
    @PostMapping("/findMedicalTechnologyList")
    public String findMedicalTechnologyList(@RequestBody @Valid FindMedicalTechnologyListByPatient medicalTechnology) {
        return medicaltechnologyService.findMedicalTechnologyList(medicalTechnology);
    }

    @ApiOperation(value = "获取常规项检查")
    @GetMapping("/getMedicalClassificationList/{id}")
    public String getMedicalClassificationList(@PathVariable String id) {
        return medicalclassificationService.getMedicalClassificationList(id);
    }

    @ApiOperation(value = "根据科室id查询医生列表")
    @PostMapping("/findDoctorListByDepartmentId")
    public String findDoctorListByDepartmentId(@RequestBody @Valid FindDoctorListByMedical doctorListByMedical) {
        return doctorinformationService.findDoctorListByDepartmentId(doctorListByMedical);
    }

    @ApiOperation(value = "返回单独医生的值班列表")
    @GetMapping("/findDoctorDutyTimeList/{doctorId}")
    public String findDoctorDutyTimeList(@PathVariable String doctorId) {
        return medicaltechnologyService.findDoctorDutyTimeList(doctorId);
    }

    @ApiOperation(value = "查询患者绑定的预约id")
    @GetMapping("/queryBindingAppointment")
    public String queryBindingAppointment(HttpServletRequest req) {
        return medicaltechnologyreserveService.queryBindingAppointment(req);
    }

    @ApiOperation(value = "预约患者")
    @PostMapping(value = "/medicalTechnologyAppointment")
    public String medicalTechnologyAppointment(@RequestBody @Valid MedicalTechnologyAppointmentForm medicalTechnology,HttpServletRequest req){
        return medicaltechnologyreserveService.medicalTechnologyAppointment(medicalTechnology,req);
    }

    @ApiOperation(value = "查询医技订单当前支付信息")
    @GetMapping("/confirmPayment/{id}")
    public String confirmPayment(@PathVariable String id){
        return medicaltechnologyreserveService.confirmPayment(id);
    }

    @ApiOperation(value = "返回付款加密码")
    @GetMapping("/payingEncryption/{id}")
    public String payingEncryption(@PathVariable String id){
        return medicaltechnologyreserveService.payingEncryption(id);
    }

    @ApiOperation(value = "查询支付信息")
    @GetMapping("/queryPaymentInformation/{payingEncryptionId}")
    public String queryPaymentInformation(@PathVariable String payingEncryptionId){
        return medicaltechnologyreserveService.queryPaymentInformation(payingEncryptionId);
    }

    @ApiOperation(value = "支付照片")
    @GetMapping("/getQRCodeMedical/{id}")
    public String getQRCodeMedical(@PathVariable String id){
        return medicaltechnologyreserveService.getQRCodeMedical(id);
    }

    @ApiOperation(value = "修改支付状态")
    @GetMapping("/scanPayStatusMedical/{id}")
    public String scanPayStatusMedical(@PathVariable String id){
        return medicaltechnologyreserveService.scanPayStatusMedical(id);
    }

    @ApiOperation(value = "支付操作")
    @GetMapping("/paying/{id}")
    public String payingMedical(@PathVariable String id){
        return medicaltechnologyreserveService.payingMedical(id);
    }

}
