package sale.ljw.clinicsystem.backend.api.doctor.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.doctor.EndOfVisitByDoctor;
import sale.ljw.clinicsystem.backend.form.order.doctor.FindAllReserveMedicalTechnology;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyreserveService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/medicalTechnology")
public class MedicalTechnologyServlet {
    @Autowired
    private MedicaltechnologyreserveService medicaltechnologyreserveService;

    @ApiOperation(value = "获取医技医生的首页展示信息")
    @GetMapping("/findCurrentAppointmentMedicalTechnology")
    public String findCurrentAppointmentMedicalTechnology(HttpServletRequest req) {
        return medicaltechnologyreserveService.findCurrentAppointmentMedicalTechnology(req);
    }

    @ApiOperation(value = "获取首页数量信息")
    @GetMapping("/getAppointmentNumber")
    public String getAppointmentNumber(HttpServletRequest req) {
        return medicaltechnologyreserveService.getAppointmentNumber(req);
    }

    @ApiOperation(value = "获取当前医技预约数据")
    @PostMapping("/findAllReserveMedicalTechnology")
    public String findAllReserveMedicalTechnology(@RequestBody @Valid FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req) {
        return medicaltechnologyreserveService.findAllReserveMedicalTechnology(medicalTechnology, req);
    }

    @ApiOperation(value = "判断当前状态是否是已支付")
    @GetMapping("/judgmentStatusPaid/{id}")
    public String judgmentStatusPaid(@PathVariable String id) {
        return medicaltechnologyreserveService.judgmentStatusPaid(id);
    }

    @ApiOperation(value = "查找报告单上信息")
    @GetMapping("/findDiagnoseReportData/{id}")
    public String findDiagnoseReportData(@PathVariable String id, HttpServletRequest req) {
        return medicaltechnologyreserveService.findDiagnoseReportData(id, req);
    }

    @ApiOperation(value = "撤销预约")
    @GetMapping("/cancelAppointment/{id}")
    public String cancelAppointment(@PathVariable String id, HttpServletRequest req) {
        return medicaltechnologyreserveService.cancelAppointment(id, req);
    }

    @ApiOperation(value = "查询历史订单")
    @PostMapping("/findHistoricalAppointments")
    public String findHistoricalAppointments(@RequestBody @Valid FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req) {
        return medicaltechnologyreserveService.findHistoricalAppointments(medicalTechnology,req);
    }

    @ApiOperation(value = "结束就诊")
    @PostMapping("/endOfVisit")
    public String endOfVisit(@RequestBody EndOfVisitByDoctor endOfVisitByDoctor, HttpServletRequest req){
        return medicaltechnologyreserveService.endOfVisit(endOfVisitByDoctor,req);
    }

    @ApiOperation(value = "医生查看检测报告")
    @GetMapping("/viewReport/{id}")
    public String viewReport(@PathVariable String id,HttpServletRequest req){
        return medicaltechnologyreserveService.viewReport(id,req);
    }


}
