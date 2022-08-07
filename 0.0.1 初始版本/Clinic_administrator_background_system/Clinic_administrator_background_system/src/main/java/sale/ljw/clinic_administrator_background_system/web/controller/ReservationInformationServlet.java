package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinic_administrator_background_system.service.clinic.ReservationInformationService;
@RestController
@RequestMapping("/reservationInformation")
public class ReservationInformationServlet {
    @Autowired
    private ReservationInformationService reservationInformationService;

    @GetMapping("/findAllReservationInformation")
    public String findAllReservationInformation(Integer page){
        return reservationInformationService.findAll(page);
    }
    @GetMapping("/deleteById")
    public String deleteReservation(String id){
        return reservationInformationService.deleteReservation(id);
    }
    @GetMapping("findById")
    public String findById(String id){
        return reservationInformationService.findById(id);
    }
    @GetMapping("/findByName")
    public String findByName(String name,Integer page){
        return reservationInformationService.findByName(name,page);
    }

}
