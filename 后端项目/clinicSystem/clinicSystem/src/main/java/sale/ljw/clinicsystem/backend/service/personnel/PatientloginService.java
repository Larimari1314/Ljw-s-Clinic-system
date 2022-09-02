package sale.ljw.clinicsystem.backend.service.personnel;

import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientLoginByPatient;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
* @author 86155
* @description 针对表【patientLogin】的数据库操作Service
* @createDate 2022-08-07 16:02:47
*/
public interface PatientloginService extends IService<Patientlogin> {

    String login(LoginFrom login, HttpServletResponse response);

    String editPatientLoginByPatient(EditPatientLoginByPatient editPatientLogin);
}
