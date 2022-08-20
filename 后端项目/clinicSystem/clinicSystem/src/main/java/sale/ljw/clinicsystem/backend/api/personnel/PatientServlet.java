package sale.ljw.clinicsystem.backend.api.personnel;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddPatientInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditPatientInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindPatientByAdmin;
import sale.ljw.clinicsystem.backend.service.personnel.PatientinformationService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/patientInformation")
public class PatientServlet {
    /**
     * 管理员端
     */
    @Autowired
    private PatientinformationService patientinformationService;

    private MultipartFile multipartFileUpload = null;

    @PostMapping("/findAllPatient")
    @ApiOperation(value = "查找全部医生")
    public String findAllAdmin(@RequestBody @Valid FindPatientByAdmin patient) {
        return patientinformationService.findAllPatient(patient);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传信息暴漏端口")
    public void upload(@RequestBody MultipartFile file) {
        multipartFileUpload = file;
    }

    @ApiOperation(value = "更新患者信息")
    @PostMapping("/editPatientInformation")
    public String editDoctorInformation(@RequestBody @Valid EditPatientInformation patientInformation) throws IOException {
        String result = patientinformationService.editPatient(patientInformation, multipartFileUpload);
        multipartFileUpload = null;
        return result;
    }

    @ApiOperation(value = "添加患者信息")
    @PostMapping("/addPatientInformation")
    public String addDoctorInformation(@RequestBody @Valid AddPatientInformationByAdmin patientInformation) {
        return patientinformationService.addPatientInformation(patientInformation);
    }

    @ApiOperation(value = "删除患者信息")
    @PostMapping("/deleteByIdsInPatient")
    public String deleteByIdsInDoctorInformation(@RequestBody @Valid DeleteIdsBYAdmin ids) {
        return patientinformationService.deleteByIdsPatientInformation(ids);
    }

    @ApiOperation(value = "下载数据模板")
    @PostMapping("/dataTemplateDownload")
    public void userTemplateDownload(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = patientinformationService.patientTemplateDownload(response);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "下载全部数据")
    @PostMapping("/exportData")
    public void exportDataUser(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = patientinformationService.exportData(response);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "上传excel")
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFileUpload = file;
    }

    @ApiOperation(value = "导入数据")
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        return patientinformationService.analyseFile(multipartFileUpload);
    }

    @ApiOperation("获取删除数据")
    @PostMapping("/getDeleteData")
    public String getDeleteData(){
        return patientinformationService.getDeleteData();
    }

    @ApiOperation(value = "永久删除数据")
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable String id) {
        return patientinformationService.deleteById(id);
    }

    @ApiOperation(value = "恢复恢复数据")
    @GetMapping("/recoveryData/{id}")
    public String recoveryData(@PathVariable String id){
        return patientinformationService.recoveryData(id);
    }
}
