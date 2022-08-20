package sale.ljw.clinicsystem.backend.service.basic;

import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.DrugSwitchingByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Drug;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
* @author 86155
* @description 针对表【drug】的数据库操作Service
* @createDate 2022-08-07 16:05:39
*/
public interface DrugService extends IService<Drug> {

    String findAllDrug(FindDrugByAdmin drug);

    String switching(DrugSwitchingByAdmin switching);


    String editDrug(EditDrugByAdmin drug, MultipartFile multipartFile);

    String deleteDrugByIds(DeleteIdsBYAdmin ids);

    ByteArrayOutputStream drugTemplateDownload(HttpServletResponse response);

    ByteArrayOutputStream exportData(HttpServletResponse response);

    String analyseFile(MultipartFile multipartFile);

    String addDrug(AddDrugByAdmin drug,MultipartFile multipartFile);

    String getDeleteData();

    String deleteById(String id);

    String recoveryData(String id);
}
