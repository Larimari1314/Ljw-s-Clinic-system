package sale.ljw.clinicsystem.backend.api.doctor.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;

@CrossOrigin
@RestController
@RequestMapping("/doctorHome")
public class HomeServletByDoctor {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation(value = "获取主页预约情况")
    @GetMapping("/getAppointmentNumber/{doctorId}")
    private String getAppointmentNumber(@PathVariable String doctorId){
        return reserveService.getAppointmentNumberByDoctorId(doctorId);
    }
}
