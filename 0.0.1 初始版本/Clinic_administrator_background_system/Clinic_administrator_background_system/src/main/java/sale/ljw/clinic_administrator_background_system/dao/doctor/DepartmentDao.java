package sale.ljw.clinic_administrator_background_system.dao.doctor;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Department;

import java.util.ArrayList;
@Mapper
@Repository
public interface DepartmentDao {
    /**
     * 查找全部部门
     *
     * @return
     */
    public abstract ArrayList<Department> findAllDepartment();

    /**
     * 根据did查询部门
     * @return
     */
    public abstract Department findById(String id);
}
