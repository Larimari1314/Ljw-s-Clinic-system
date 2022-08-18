package sale.ljw.clinicsystem.backend.service.basic;

import sale.ljw.clinicsystem.backend.form.basic.admin.EditDoctorDuty;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditUniteDutyTimeByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【doctorDuty】的数据库操作Service
* @createDate 2022-08-07 16:05:09
*/
public interface DoctordutyService extends IService<Doctorduty> {

    String findAllDoctor(FindDoctorByAdmin doctor);

    String editDoctorDuty(EditDoctorDuty doctorDuty);
    String findDutyTime();

    String editUniteDoctorDutyTime(EditUniteDutyTimeByAdmin dutyTime);
}
