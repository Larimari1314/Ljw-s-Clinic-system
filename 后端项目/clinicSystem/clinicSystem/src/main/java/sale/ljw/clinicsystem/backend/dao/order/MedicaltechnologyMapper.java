package sale.ljw.clinicsystem.backend.dao.order;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.basic.admin.DisplayDataMedicalTechnologyByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllMedicalTechnologyByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindMedicalTechnologyOrderByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnology;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【medicalTechnology】的数据库操作Mapper
* @createDate 2022-09-21 07:13:48
* @Entity sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnology
*/
@Mapper
@Repository
public interface MedicaltechnologyMapper extends BaseMapper<Medicaltechnology> {

    ArrayList<Map<String, Object>> getMedicalProjectNameList();
    ArrayList<Map<String, Object>> findMedicalTechnologyList(FindMedicalTechnologyListByPatient medicalTechnology);

    ArrayList<DisplayDataMedicalTechnologyByAdmin> findAllMedicalTechnology(FindAllMedicalTechnologyByAdmin medicalTechnology);

    ArrayList<Map<String, Object>> findMedicalTechnologyOrder(FindMedicalTechnologyOrderByAdmin medicalTechnologyOrder);
}




