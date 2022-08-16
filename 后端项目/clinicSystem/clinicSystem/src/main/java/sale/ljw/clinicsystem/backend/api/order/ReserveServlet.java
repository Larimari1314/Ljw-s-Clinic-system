package sale.ljw.clinicsystem.backend.api.order;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/reserve")
public class ReserveServlet {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation(value = "查找全部预约单子")
    @PostMapping("/findReserveNotViewed")
    public String findReserveNotViewed(@RequestBody @Valid FindReserveNotViewedByAdmin reserveNotViewed) {
        return reserveService.findReserveNotViewed(reserveNotViewed);
    }
    @ApiOperation(value = "撤销预约")
    @PostMapping("/cancelAppointment")
    public String cancelAppointment(@RequestBody @Valid DeleteIdsBYAdmin ids){
        return reserveService.cancelAppointment(ids);
    }
    @ApiOperation(value = "撤销预约单查看")
    @PostMapping("/cancelAppointmentViews")
    public String cancelAppointmentViews(@RequestBody @Valid FindReserveNotViewedByAdmin reserveNotViewed){
        return reserveService.cancelAppointmentViews(reserveNotViewed);
    }
    @ApiOperation(value = "恢复预约")
    @PostMapping("/resumeAppointment")
    public String resumeAppointment(@RequestBody @Valid DeleteIdsBYAdmin ids){
        return reserveService.resumeAppointment(ids);
    }
}
