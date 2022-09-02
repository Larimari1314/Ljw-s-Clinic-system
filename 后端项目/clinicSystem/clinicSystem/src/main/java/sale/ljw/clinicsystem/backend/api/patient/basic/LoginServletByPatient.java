package sale.ljw.clinicsystem.backend.api.patient.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.service.personnel.PatientloginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/loginPatient")
public class LoginServletByPatient {
    @Autowired
    private PatientloginService patientloginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginFrom login, HttpServletResponse response){
        return patientloginService.login(login,response);
    }
}
