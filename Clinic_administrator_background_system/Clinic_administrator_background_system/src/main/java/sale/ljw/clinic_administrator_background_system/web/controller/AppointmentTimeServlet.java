package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinic_administrator_background_system.service.clinic.AppointmentTimeNumberService;
import sale.ljw.clinic_administrator_background_system.service.clinic.AppointmentTimeService;

@RestController
@RequestMapping("/appointment")
public class AppointmentTimeServlet {
    @Autowired
    private AppointmentTimeService appointmentTimeService;

    @Autowired
    private AppointmentTimeNumberService appointmentTimeNumberService;
    @GetMapping("/findAllTime")
    public String findAllTime() {
        return appointmentTimeService.findAllTime();
    }

    @GetMapping("/findTimeMorning")
    public String findTimeMorning(String id) {
        return appointmentTimeService.findTimeMorning(id);
    }

    @GetMapping("/findTimeAfternoon")
    public String findTimeAfternoon(String id) {
        return appointmentTimeService.findTimeAfternoon(id);
    }

    @GetMapping("/findSwitch")
    public String findSwitch(Integer page) {
        return appointmentTimeNumberService.findAll(page);
    }

    @GetMapping("/exitNumber")
    public String exitNumber(String id,String text){
        return appointmentTimeNumberService.exitNumber(id,text);
    }
}
