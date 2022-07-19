package sale.ljw.clinic_administrator_background_system.web.controller;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;
import sale.ljw.clinic_administrator_background_system.service.patient.Impl.PatientInformationServiceImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
@RequestMapping("/patient")
public class PatientServlet {
    private static String fileNameNew=null;
    private static MultipartFile multipartFile;
    @Autowired
    private PatientInformationServiceImpl patientService;

    @GetMapping("/findAllPatient")
    @ResponseBody
    public String findAllPatientTest(Integer page) {
        return JSON.toJSONString(patientService.findAllPatient(page));
    }

    @GetMapping("/findByNamePatient")
    @ResponseBody
    public String findByNamePatient(Integer page, String name) {
        return JSON.toJSONString(patientService.findByNamePatient(name, page));
    }

    @GetMapping("/removePatientById")
    @ResponseBody
    public String removePatientById(String id) {
        return patientService.removePatientById(id);
    }

    @GetMapping("/batchRemovePatient")
    @ResponseBody
    public String batchRemovePatient(String ids) {
        return patientService.batchRemovePatient(ids);
    }

    @PostMapping("/addPatient")
    @ResponseBody
    public String addPatient(@RequestBody Patient_information params) {
        return patientService.addOnePatient(params);
    }

    @PostMapping("/editPatient")
    @ResponseBody
    public String editPatient(@RequestBody Patient_information params) {
        return patientService.editPatient(params);
    }

    @PostMapping("/exportData")
    public void patientExportData(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName=new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName="+fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url=System.getProperty("user.dir")+"\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\patient\\Patient_information.java";
        ByteArrayOutputStream os = patientService.patientExportData(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    @PostMapping("/dataTemplateDownload")
    public void patientTemplateDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName=new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName="+fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url=System.getProperty("user.dir")+"\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\patient\\Patient_information.java";
        ByteArrayOutputStream os = patientService.patientTemplateDownload(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 接收前端发送过来的数据
     * @param file
     * @return
     */
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFile=file;
        fileNameNew= UUID.randomUUID().toString();
    }

    /**
     * 向前端传达是否上传成功的信息
     * @return
     * @throws IOException
     */
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        String path = System.getProperty("user.dir");
        File newFile=new File(path+"\\src\\main\\webapp\\static\\"+fileNameNew+".xlsx");
        multipartFile.transferTo(newFile);
        Message message = patientService.analyseFile(fileNameNew);
        return JSON.toJSONString(message);
    }
}
