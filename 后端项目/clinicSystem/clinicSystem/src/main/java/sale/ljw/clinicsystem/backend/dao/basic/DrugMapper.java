package sale.ljw.clinicsystem.backend.dao.basic;

import sale.ljw.clinicsystem.backend.form.basic.admin.FindDrugByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Drug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【drug】的数据库操作Mapper
* @createDate 2022-08-07 16:05:39
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.Drug
*/
public interface DrugMapper extends BaseMapper<Drug> {
    public abstract ArrayList<Map<String, Objects>> findAllByDrug(FindDrugByAdmin drug);
}




