package sale.ljw.clinicsystem.backend.service.order;

import sale.ljw.clinicsystem.backend.form.basic.admin.EditMedicalClassificationByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllMedicalClassificationByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Medicalclassification;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【medicalClassification】的数据库操作Service
* @createDate 2022-09-21 07:13:31
*/
public interface MedicalclassificationService extends IService<Medicalclassification> {

    String getMedicalClassificationList(String id);

    String findAllMedicalClassification(FindAllMedicalClassificationByAdmin medicalClassification);

    String editMedicalClassification(EditMedicalClassificationByAdmin medicalClassification);
}
