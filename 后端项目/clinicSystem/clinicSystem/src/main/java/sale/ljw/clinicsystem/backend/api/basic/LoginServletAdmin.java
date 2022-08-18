package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinicsystem.backend.service.basic.LandingadsService;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginServletAdmin {
    @Autowired
    private LandingadsService landingadsService;
    @ApiOperation(value = "获取界面广告")
    @GetMapping("/GetAllAds")
    public String getAllAds(){
      return  landingadsService.getAllAds();
    }
}
