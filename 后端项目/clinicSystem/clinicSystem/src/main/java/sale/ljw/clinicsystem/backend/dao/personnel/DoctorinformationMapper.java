package sale.ljw.clinicsystem.backend.dao.personnel;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindDoctorListByMedical;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【doctorInformation】的数据库操作Mapper
* @createDate 2022-08-07 16:00:54
* @Entity sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation
*/
@Mapper
@Repository
public interface DoctorinformationMapper extends BaseMapper<Doctorinformation> {
    /*管理员端*/
    /**
     * 查找全部医生用户
     */
    public abstract ArrayList<Map<String, Objects>> findAllDoctor(FindDoctorByAdmin doctor);

    ArrayList<Map<String,Objects>> mainGetNumber();
    Map<String, Object> findValueNumber();
    public List<Map<String,Object>> getDayMedicine();

    List<Map<String,Object>> getStateStatistics();

    List<Map<String, Object>> getRegisDoctor();
    List<Map<String, Object>> getDeleteData();
    Integer permanentDeleteById(String id);
    Doctorinformation selectDeleteDataById(String id);
    Integer recoveryData(String id);

    List<Map<String, Object>> getDoctorListByDepartment(String departmentId);

    ArrayList<Map<String, Object>> findDoctorListByDepartmentId(FindDoctorListByMedical doctorListByMedical);
    String findDoctorDutyTime(String doctorId);
}




