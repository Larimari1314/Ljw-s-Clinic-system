package sale.ljw.clinicsystem.backend.dao.basic;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.basic.Registrationlevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【registrationLevel】的数据库操作Mapper
* @createDate 2022-08-07 16:07:34
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.Registrationlevel
*/
@Mapper
@Repository
public interface RegistrationlevelMapper extends BaseMapper<Registrationlevel> {
    public ArrayList<Map<String,Object>> findAllRegistrationLevel();
}




