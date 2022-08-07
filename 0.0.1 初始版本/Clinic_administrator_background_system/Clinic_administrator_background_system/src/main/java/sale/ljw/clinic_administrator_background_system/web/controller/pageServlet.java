package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinic_administrator_background_system.service.page.LandingAdsService;

@RestController
@RequestMapping("/page")
public class pageServlet {
    @Autowired
    private LandingAdsService landingAdsService;
    @GetMapping("/AllAds")
    public String findAllAds() {
        return landingAdsService.findALlAds();
    }
}
