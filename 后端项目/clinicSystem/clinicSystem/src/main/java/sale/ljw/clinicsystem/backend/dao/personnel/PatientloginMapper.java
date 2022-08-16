package sale.ljw.clinicsystem.backend.dao.personnel;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86155
* @description 针对表【patientLogin】的数据库操作Mapper
* @createDate 2022-08-07 16:02:47
* @Entity sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin
*/
@Mapper
@Repository
public interface PatientloginMapper extends BaseMapper<Patientlogin> {
    public abstract Integer updateByIdToPermission(String id);
}




