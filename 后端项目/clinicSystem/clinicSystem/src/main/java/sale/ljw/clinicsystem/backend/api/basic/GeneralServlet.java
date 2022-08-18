package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditGeneralByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllGeneralByAdmin;
import sale.ljw.clinicsystem.backend.service.basic.GeneralService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/general")
public class GeneralServlet {
    @Autowired
    private GeneralService generalService;

    @ApiOperation(value = "获取性别列表")
    @GetMapping("/findSex")
    public String findSexCoding() {
        return generalService.findSexCoding();
    }

    @ApiOperation(value = "获取班次列表")
    @GetMapping("/findDutyTimeCoding")
    public String findDutyTimeCoding() {
        return generalService.findDutyTimeCoding();
    }

    @ApiOperation(value = "获取值班列表")
    @GetMapping("/findAllByAllDuty")
    public String findAllByAllDuty() {
        return generalService.findAllByAllDuty();
    }

    @ApiOperation(value = "获取支付状态信息")
    @GetMapping("/findPayInformation")
    public String findPayInformation() {
        return generalService.findPayInformation();
    }

    @ApiOperation(value = "获取药品规格列表")
    @GetMapping("/findDrugSpecification")
    public String findDrugSpecification(){
        return generalService.findDrugSpecification();
    }
    @ApiOperation(value = "获取全部常规项")
    @PostMapping("/getAllGeneral")
    public String getAllGeneral(@RequestBody @Valid FindAllGeneralByAdmin general) {

        return generalService.getAllGeneral(general);
    }

    @ApiOperation(value = "编辑常规项")
    @PostMapping("/editGeneral")
    public String editGeneral(@RequestBody @Valid EditGeneralByAdmin general) {
        return generalService.editGeneral(general);
    }
}
