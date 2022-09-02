package sale.ljw.clinicsystem.backend.service.personnel;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddPatientInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditPatientInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindPatientByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientInformationByPatient;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientinformation;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * @author 86155
 * @description 针对表【patientInformation】的数据库操作Service
 * @createDate 2022-08-07 16:02:23
 */
public interface PatientinformationService extends IService<Patientinformation> {

    public abstract String findAllPatient(FindPatientByAdmin patient);

    public abstract String editPatient(EditPatientInformation patientinformation, MultipartFile multipartFileUpload);

    public abstract String addPatientInformation(AddPatientInformationByAdmin patientInformation);

    public abstract String deleteByIdsPatientInformation(DeleteIdsBYAdmin ids);

    public abstract ByteArrayOutputStream patientTemplateDownload(HttpServletResponse response);

    public abstract ByteArrayOutputStream exportData(HttpServletResponse response);

    public abstract String analyseFile(MultipartFile multipartFileUpload);

    public abstract String getDeleteData();

    public abstract String deleteById(String id);

    public abstract String recoveryData(String id);

    public abstract String editPatientByPatient(EditPatientInformationByPatient patientInformation, MultipartFile multipartFile);
}
