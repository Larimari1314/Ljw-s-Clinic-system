package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditRegistrationLevelByAdmin;
import sale.ljw.clinicsystem.backend.service.basic.RegistrationlevelService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/registrationLevel")
public class registrationLevelServlet {
    @Autowired
    private RegistrationlevelService registrationlevelService;

    @PostMapping("/findAllRegistrationLevel")
    @ApiOperation(value = "查找挂号级别")
    public String findAllRegistrationLevel() {
        return registrationlevelService.findAllRegistrationLevel();
    }

    @PostMapping("/editRegistrationLevel")
    @ApiOperation(value = "编辑挂号级别")
    public String editRegistrationLevel(@RequestBody @Valid EditRegistrationLevelByAdmin registrationLevel) {
        return registrationlevelService.editRegistrationLevel(registrationLevel);
    }
}
