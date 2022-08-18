package sale.ljw.clinicsystem.backend.service.basic;

import sale.ljw.clinicsystem.backend.form.basic.admin.EditRegistrationLevelByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Registrationlevel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【registrationLevel】的数据库操作Service
* @createDate 2022-08-07 16:07:34
*/
public interface RegistrationlevelService extends IService<Registrationlevel> {

    String findAllRegistrationLevel();

    String editRegistrationLevel(EditRegistrationLevelByAdmin registrationLevel);
}
