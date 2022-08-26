package sale.ljw.clinicsystem.backend.api.doctor.personnel;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.doctor.EditDoctorInformationByDoctor;
import sale.ljw.clinicsystem.backend.form.personnel.doctor.EditDoctorLoginByDoctor;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorloginService;

@CrossOrigin
@RestController
@RequestMapping("/doctorInformationByDoctor")
public class DoctorInformationServletByDoctor {
    @Autowired
    private DoctorinformationService doctorinformationService;
    @Autowired
    private DoctorloginService doctorloginService;
    private MultipartFile multipartFile = null;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "编辑个人信息")
    @PostMapping("/editDoctorInformation")
    private String editDoctorInformation(@RequestBody EditDoctorInformationByDoctor doctorInformation) {
        String result=doctorinformationService.editDoctorByDoctor(doctorInformation, multipartFile);
        multipartFile=null;
        return result;
    }

    @ApiOperation(value = "修改登录信息")
    @PostMapping("/editDoctorLogin")
    public String editDoctorLogin(@RequestBody EditDoctorLoginByDoctor editDoctorLogin){
        return doctorloginService.editDoctorLoginByDoctor(editDoctorLogin);
    }
}
