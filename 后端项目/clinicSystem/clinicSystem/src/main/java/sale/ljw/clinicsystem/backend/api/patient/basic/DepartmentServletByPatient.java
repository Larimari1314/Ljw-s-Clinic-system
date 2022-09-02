package sale.ljw.clinicsystem.backend.api.patient.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.order.doctor.ConfirmAppointmentByDoctor;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/departmentByPatient")
public class DepartmentServletByPatient {
    @Autowired
    private DoctorinformationService doctorinformationService;
    @Autowired
    private ReserveService reserveService;
    //获取科室对应的医生
    @ApiOperation("获取科室对应的医师")
    @GetMapping("/getDoctorListByDepartment/{departmentId}")
    public String getDoctorListByDepartment(@PathVariable String departmentId){
        return doctorinformationService.getDoctorListByDepartment(departmentId);
    }

    @ApiOperation("获取预约信息返回支付编号")
    @PostMapping("/getAppointmentInformation")
    public String getAppointmentInformation(@RequestBody @Valid ConfirmAppointmentByDoctor confirmAppointment){
        return reserveService.getAppointmentInformation(confirmAppointment);
    }
    @ApiOperation(value = "返回支付图片")
    @GetMapping("/getQRCode/{payId}")
    public String getQRCode(@PathVariable String payId){
        return reserveService.getQRCode(payId);
    }

    @ApiOperation(value = "获取预约信息，展示支付界面")
    @GetMapping("/getPayAppointmentInformation/{payId}")
    public String getPayAppointmentInformation(@PathVariable String payId){
        return reserveService.getPayAppointmentInformation(payId);
    }
    @ApiOperation(value = "扫描二维码修改支付状态")
    @GetMapping("/scanPayStatus/{payId}")
    public String scanPayStatus(@PathVariable String payId){
        return reserveService.scanPayStatus(payId);
    }
    @ApiOperation(value = "检测支付状态")
    @GetMapping("/paymentStatus/{payId}")
    public String paymentStatus(@PathVariable String payId){
        return reserveService.paymentStatus(payId);
    }
    @ApiOperation(value = "支付操作")
    @GetMapping("/payAppointment/{payId}")
    public String payAppointment(@PathVariable String payId){
        return reserveService.payAppointment(payId);
    }
}
