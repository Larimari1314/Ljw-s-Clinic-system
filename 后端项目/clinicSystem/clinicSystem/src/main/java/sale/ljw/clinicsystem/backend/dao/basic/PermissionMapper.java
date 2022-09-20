package sale.ljw.clinicsystem.backend.dao.basic;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.basic.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86155
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2022-08-07 16:13:22
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.Permission
*/
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}




