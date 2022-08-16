package sale.ljw.clinicsystem.backend.dao.basic;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.basic.General;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【general】的数据库操作Mapper
* @createDate 2022-08-07 16:06:20
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.General
*/
@Mapper
@Repository
public interface GeneralMapper extends BaseMapper<General> {
    public abstract ArrayList<Map<String, Objects>> findDutyTimeCoding();
    public abstract ArrayList<Map<String, Objects>> selectAllByAllDuty();

}




