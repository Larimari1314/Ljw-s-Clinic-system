package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminInformation;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminLogin;
import sale.ljw.clinic_administrator_background_system.service.clinic.AdminInformationService;
import sale.ljw.clinic_administrator_background_system.service.login.AdminLoginService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/adminInformation")
public class adminInformationServlet {
    private static MultipartFile multipartFile;
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private AdminInformationService adminInformationService;

    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @PostMapping("/editAdminInformation")
    public String editAdminInformation(@RequestBody AdminInformation adminInformation) throws IOException {
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File file = new File(path + "\\src\\main\\webapp\\img\\admin\\" + adminInformation.getId() + "\\" + adminInformation.getId() + ".jpg");
            //文件进行保存
            multipartFile.transferTo(file);
            //查看数据
            return adminInformationService.editAdmin(adminInformation);
        } else {
            return adminInformationService.editAdmin(adminInformation);
        }
    }
    @PostMapping("/editAdminLogin")
    public String editAdminLogin(@RequestBody AdminLogin adminLogin){
        return adminLoginService.editAdminLogin(adminLogin);
    }
}
