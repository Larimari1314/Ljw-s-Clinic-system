package sale.ljw.clinicsystem.backend.dao.basic;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【doctorDuty】的数据库操作Mapper
* @createDate 2022-08-07 16:05:09
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty
*/
@Mapper
@Repository
public interface DoctordutyMapper extends BaseMapper<Doctorduty> {

}




