package sale.ljw.clinicsystem.backend.api.personnel;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddDoctorInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditDoctorInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/doctorInformation")
public class DoctorServlet {
    /**
     * 管理员端
     */
    @Autowired
    private DoctorinformationService doctorinformationService;

    private MultipartFile multipartFileUpload = null;

    @PostMapping("/findAllDoctor")
    @ApiOperation(value = "查找全部医生")
    public String findAllAdmin(@RequestBody @Valid FindDoctorByAdmin doctor) {
        return doctorinformationService.findAllDoctor(doctor);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传信息暴漏端口")
    public void upload(@RequestBody MultipartFile file) {
        multipartFileUpload = file;
    }

    @ApiOperation(value = "更新医生信息")
    @PostMapping("/editDoctorInformation")
    public String editDoctorInformation(@RequestBody @Valid EditDoctorInformation doctorInformation) throws IOException {
        String result = doctorinformationService.editDoctor(doctorInformation, multipartFileUpload);
        multipartFileUpload = null;
        return result;
    }

    @ApiOperation(value = "添加医生信息")
    @PostMapping("/addDoctorInformation")
    public String addDoctorInformation(@RequestBody @Valid AddDoctorInformationByAdmin doctorInformation) {
        return doctorinformationService.addDoctorInformation(doctorInformation);
    }

    @ApiOperation(value = "删除医生信息")
    @PostMapping("/deleteByIdsInDoctor")
    public String deleteByIdsInDoctorInformation(@RequestBody @Valid DeleteIdsBYAdmin ids) {
        return doctorinformationService.deleteByIdsInDoctorInformation(ids);
    }

    @ApiOperation(value = "下载数据模板")
    @PostMapping("/dataTemplateDownload")
    public void userTemplateDownload(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = doctorinformationService.doctorTemplateDownload(response);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "下载全部数据")
    @PostMapping("/exportData")
    public void exportDataUser(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = doctorinformationService.exportData(response);
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
        return doctorinformationService.analyseFile(multipartFileUpload);
    }
}
