package sale.ljw.clinicsystem.backend.api.doctor.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorloginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/loginDoctor")
public class LoginServletByDoctor {
    @Autowired
    private DoctorloginService doctorloginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginFrom login, HttpServletResponse response){
        return doctorloginService.login(login,response);
    }
}
