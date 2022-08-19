package sale.ljw.clinicsystem.backend.service.personnel;

import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminLogin;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.pojo.personnel.Adminlogin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
* @author 86155
* @description 针对表【adminLogin】的数据库操作Service
* @createDate 2022-08-07 16:00:14
*/
public interface AdminloginService extends IService<Adminlogin> {

    String getAdminImgUrl(String loginCredentials);

    String login(LoginFrom login, HttpServletResponse response);

    String logout(HttpServletResponse response);

    String aditAdminInformation(EditAdminInformationByAdmin adminInformation, MultipartFile multipartFile);

    String editAdminLogin(EditAdminLogin adminLogin);
}
