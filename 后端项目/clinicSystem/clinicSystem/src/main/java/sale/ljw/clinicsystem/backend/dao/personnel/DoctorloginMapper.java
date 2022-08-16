package sale.ljw.clinicsystem.backend.dao.personnel;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【doctorLogin】的数据库操作Mapper
* @createDate 2022-08-07 16:01:25
* @Entity sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin
*/
@Mapper
@Repository
public interface DoctorloginMapper extends BaseMapper<Doctorlogin> {
    public abstract Integer updateByIdToPermission(String id);
}




