package sale.ljw.clinicsystem.backend.api.admin.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminLogin;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.service.basic.LandingadsService;
import sale.ljw.clinicsystem.backend.service.personnel.AdminloginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/loginAdmin")
public class LoginServletAdmin {
    @Autowired
    private LandingadsService landingadsService;
    @Autowired
    private AdminloginService adminloginService;

    private MultipartFile multipartFile;
    @ApiOperation(value = "获取界面广告")
    @GetMapping("/GetAllAds")
    public String getAllAds() {
        return landingadsService.getAllAds();
    }

    @ApiOperation(value = "获取登录头像")
    @GetMapping("/getAdminImgUrl/{loginCredentials}")
    public String getAdminImgUrl(@PathVariable String loginCredentials) {
        return adminloginService.getAdminImgUrl(loginCredentials);
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginFrom login, HttpServletResponse response){
        return adminloginService.login(login,response);
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public String logOut(HttpServletResponse response){
        return adminloginService.logout(response);
    }
    @ApiOperation(value = "上传头像")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file){
       multipartFile=file;
    }
    @ApiOperation(value = "修改管理员信息")
    @PostMapping("/editAdminInformation")
    public String editAdminInformation(@RequestBody EditAdminInformationByAdmin adminInformation){
        return adminloginService.aditAdminInformation(adminInformation,multipartFile);
    }
    @ApiOperation(value = "修改登录信息")
    @PostMapping("/editAdminLogin")
    public String editAdminLogin(@RequestBody EditAdminLogin adminLogin){
        return adminloginService.editAdminLogin(adminLogin);
    }
}
