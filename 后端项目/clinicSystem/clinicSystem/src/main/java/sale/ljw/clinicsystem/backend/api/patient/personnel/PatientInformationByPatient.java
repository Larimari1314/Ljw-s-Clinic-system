package sale.ljw.clinicsystem.backend.api.patient.personnel;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientInformationByPatient;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientLoginByPatient;
import sale.ljw.clinicsystem.backend.form.personnel.patient.RegisterAccountPatient;
import sale.ljw.clinicsystem.backend.service.personnel.PatientinformationService;
import sale.ljw.clinicsystem.backend.service.personnel.PatientloginService;

@CrossOrigin
@RestController
@RequestMapping("/patientInformationByPatient")
public class PatientInformationByPatient {
    @Autowired
    private PatientinformationService patientinformationService;
    @Autowired
    private PatientloginService patientloginService;
    private MultipartFile multipartFile = null;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "编辑个人信息")
    @PostMapping("/editPatientInformation")
    private String editDoctorInformation(@RequestBody EditPatientInformationByPatient patientInformation) {
        String result = patientinformationService.editPatientByPatient(patientInformation, multipartFile);
        multipartFile = null;
        return result;
    }

    @ApiOperation(value = "修改登录信息")
    @PostMapping("/editPatientLogin")
    public String editDoctorLogin(@RequestBody EditPatientLoginByPatient editPatientLogin) {
        return patientloginService.editPatientLoginByPatient(editPatientLogin);
    }

    @ApiOperation(value = "注册账号")
    public String registerAccountByPatient(@RequestBody RegisterAccountPatient patient){

    }
}
