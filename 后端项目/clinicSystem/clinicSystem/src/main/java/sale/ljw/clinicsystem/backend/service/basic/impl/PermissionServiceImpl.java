package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import sale.ljw.clinicsystem.backend.pojo.basic.Permission;
import sale.ljw.clinicsystem.backend.service.basic.PermissionService;
import sale.ljw.clinicsystem.backend.dao.basic.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2022-08-07 16:13:22
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




