package sale.ljw.clinicsystem.backend.api.patient.order;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllCurrentAppointmentByPatient;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;

@CrossOrigin
@RestController
@RequestMapping("/currentAppointmentByPatient")
public class CurrentAppointmentServlet {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation(value = "获取当前预约")
    @PostMapping("/getAllCurrentAppointment")
    public String getAllCurrentAppointment(@RequestBody FindAllCurrentAppointmentByPatient currentAppointment){
            return reserveService.getAllCurrentAppointment(currentAppointment);
    }

    @ApiOperation(value = "撤销预约")
    @GetMapping("/cancelAppointment/{orderId}")
    public String cancelAppointment(@PathVariable String orderId){
        return reserveService.cancelAppointmentBypatient(orderId);
    }
}
