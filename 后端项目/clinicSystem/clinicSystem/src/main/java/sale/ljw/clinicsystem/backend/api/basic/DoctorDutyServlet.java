package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDoctorDuty;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditUniteDutyTimeByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.service.basic.DoctordutyService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/doctorDuty")
public class DoctorDutyServlet {
    @Autowired
    private DoctordutyService doctordutyService;

    @PostMapping("/findAllDoctorDuty")
    @ApiOperation(value = "查找全部医生")
    public String findAllDoctor(@RequestBody @Valid FindDoctorByAdmin doctor) {
        return doctordutyService.findAllDoctor(doctor);
    }

    @PostMapping("/editDoctorDuty")
    @ApiOperation(value = "修改预约时间")
    public String editDoctorDuty(@RequestBody @Valid EditDoctorDuty doctorDuty){
        return doctordutyService.editDoctorDuty(doctorDuty);
    }
    @PostMapping("/findDutyTime")
    @ApiOperation(value = "查询预约时间")
    public String findDutyTime(){
        return doctordutyService.findDutyTime();
    }
    @ApiOperation(value = "统一修改医生值班信息")
    @PostMapping("/editUniteDoctorDutyTime")
    public String editUniteDoctorDutyTime(@RequestBody @Valid EditUniteDutyTimeByAdmin dutyTime){
        return doctordutyService.editUniteDoctorDutyTime(dutyTime);
    }

}
