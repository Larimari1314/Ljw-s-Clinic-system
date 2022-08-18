package sale.ljw.clinicsystem.backend.service.personnel;

import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddDoctorInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditDoctorInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
* @author 86155
* @description 针对表【doctorInformation】的数据库操作Service
* @createDate 2022-08-07 16:00:54
*/
public interface DoctorinformationService extends IService<Doctorinformation> {
    public abstract String findAllDoctor(FindDoctorByAdmin doctor);
    public abstract String editDoctor(EditDoctorInformation doctorInformation, MultipartFile file);
    public abstract String addDoctorInformation(AddDoctorInformationByAdmin doctorInformationByAdmin);
    public abstract String deleteByIdsInDoctorInformation(DeleteIdsBYAdmin ids);

    public abstract ByteArrayOutputStream doctorTemplateDownload(HttpServletResponse response);


    public abstract ByteArrayOutputStream exportData(HttpServletResponse response);

    public abstract String analyseFile(MultipartFile multipartFileUpload);

    public abstract String mainGetNumber();

    public  abstract String getValueNumber();

   public abstract String getDayMedicine();

   public abstract String getStateStatistics();

    public abstract String getRegisDoctor();
}
