package sale.ljw.clinicsystem.backend.dao.personnel;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindPatientByAdmin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientinformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【patientInformation】的数据库操作Mapper
* @createDate 2022-08-07 16:02:23
* @Entity sale.ljw.clinicsystem.backend.pojo.personnel.Patientinformation
*/
@Mapper
@Repository
public interface PatientinformationMapper extends BaseMapper<Patientinformation> {
    public abstract ArrayList<Map<String,Object>> findAllPatient(FindPatientByAdmin patient);
}




