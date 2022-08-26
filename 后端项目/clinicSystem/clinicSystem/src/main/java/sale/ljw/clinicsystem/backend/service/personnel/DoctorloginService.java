package sale.ljw.clinicsystem.backend.service.personnel;

import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.form.personnel.doctor.EditDoctorLoginByDoctor;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
* @author 86155
* @description 针对表【doctorLogin】的数据库操作Service
* @createDate 2022-08-07 16:01:25
*/
public interface DoctorloginService extends IService<Doctorlogin> {

    String login(LoginFrom login, HttpServletResponse response);

    String editDoctorLoginByDoctor(EditDoctorLoginByDoctor editDoctorLogin);
}
