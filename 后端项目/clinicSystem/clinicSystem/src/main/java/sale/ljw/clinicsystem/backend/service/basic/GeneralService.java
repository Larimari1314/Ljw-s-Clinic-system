package sale.ljw.clinicsystem.backend.service.basic;

import sale.ljw.clinicsystem.backend.form.basic.admin.EditGeneralByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllGeneralByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.General;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【general】的数据库操作Service
* @createDate 2022-08-07 16:06:20
*/
public interface GeneralService extends IService<General> {
    public abstract String findSexCoding();

   public abstract String findDutyTimeCoding();
    public abstract String findAllByAllDuty();

    public abstract String findPayInformation();

    public abstract String getAllGeneral(FindAllGeneralByAdmin general);

    public abstract String editGeneral(EditGeneralByAdmin general);

    public abstract String findDrugSpecification();

}
