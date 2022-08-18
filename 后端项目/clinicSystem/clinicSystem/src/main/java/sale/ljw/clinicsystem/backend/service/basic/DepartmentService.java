package sale.ljw.clinicsystem.backend.service.basic;

import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【department】的数据库操作Service
* @createDate 2022-08-07 16:04:27
*/
public interface DepartmentService extends IService<Department> {
    public abstract String findDepartmentCoding();

    String findAllDepartment(FindDepartmentByAdmin department);

    String editDepartment(EditDepartmentByAdmin department, MultipartFile multipartFile);

    String addDepartment(AddDepartmentByAdmin department, MultipartFile multipartFile);
}
