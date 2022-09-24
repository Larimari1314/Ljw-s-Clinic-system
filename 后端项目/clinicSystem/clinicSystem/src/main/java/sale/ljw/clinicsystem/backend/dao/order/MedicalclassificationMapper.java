package sale.ljw.clinicsystem.backend.dao.order;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.order.Medicalclassification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【medicalClassification】的数据库操作Mapper
* @createDate 2022-09-21 07:13:31
* @Entity sale.ljw.clinicsystem.backend.pojo.order.Medicalclassification
*/
@Mapper
@Repository
public interface MedicalclassificationMapper extends BaseMapper<Medicalclassification> {
    ArrayList<Map<String,Object>> getMedicalClassificationList(String id);
}




