package sale.ljw.clinicsystem.backend.service.basic;

import sale.ljw.clinicsystem.backend.pojo.basic.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【department】的数据库操作Service
* @createDate 2022-08-07 16:04:27
*/
public interface DepartmentService extends IService<Department> {
    public abstract String findDepartmentCoding();
}
