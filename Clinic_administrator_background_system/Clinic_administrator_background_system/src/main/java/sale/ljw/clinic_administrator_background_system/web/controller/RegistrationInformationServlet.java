package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinic_administrator_background_system.service.clinic.RegistrationInformationService;

@RestController
@RequestMapping("/registrationInformation")
public class RegistrationInformationServlet {
    @Autowired
    private RegistrationInformationService registrationInformationService;
    @GetMapping("findById")
    public String findById(String id) {
        return registrationInformationService.findById(id);
    }

}
