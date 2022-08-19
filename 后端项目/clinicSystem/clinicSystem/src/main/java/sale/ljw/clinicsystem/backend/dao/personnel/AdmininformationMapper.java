package sale.ljw.clinicsystem.backend.dao.personnel;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.personnel.Admininformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86155
* @description 针对表【adminInformation】的数据库操作Mapper
* @createDate 2022-08-07 15:58:09
* @Entity sale.ljw.clinicsystem.backend.pojo.personnel.Admininformation
*/
@Mapper
@Repository
public interface AdmininformationMapper extends BaseMapper<Admininformation> {

}




