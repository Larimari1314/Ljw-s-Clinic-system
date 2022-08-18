package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;

@CrossOrigin
@RestController
@RequestMapping("/adminHome")
public class AdminHomeServlet {
    @Autowired
    private DoctorinformationService doctorinformationService;

    @ApiOperation(value = "管理员主页获取全部数据")
    @GetMapping("/mainGetNumber")
    public String mainGetNumber() {
        return doctorinformationService.mainGetNumber();
    }

    @ApiOperation(value = "获取echars中的数据")
    @GetMapping("/getValueNumber")
    public String getValueNumber(){
        return doctorinformationService.getValueNumber();
    }

    @ApiOperation(value = "获取echars中的数据")
    @GetMapping("/getDayNumber")
    public String getDayNumber(){
        return doctorinformationService.getDayMedicine();
    }

    @ApiOperation(value = "获取echars中的数据")
    @GetMapping("/getStateStatistics")
    public String getStateStatistics(){
        return doctorinformationService.getStateStatistics();
    }
    @ApiOperation(value = "获取挂号级别医生人数")
    @GetMapping("/getRegisDoctor")
    public String getRegisDoctor() {
        return doctorinformationService.getRegisDoctor();
    }
}
