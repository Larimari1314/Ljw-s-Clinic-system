package sale.ljw.clinic_administrator_background_system.web.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;
import sale.ljw.clinic_administrator_background_system.service.doctor.DoctorInformationService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
@RequestMapping("/doctor")
public class DoctorServlet {
    private static String fileNameNew = null;
    private static MultipartFile multipartFile;
    @Autowired
    private DoctorInformationService doctorService;

    @GetMapping("/findAllDoctor")
    @ResponseBody
    public String findAllDoctorTest(Integer page) {
        return doctorService.findAllDoctor(page);
    }

    @GetMapping("/findByNameDoctor")
    @ResponseBody
    public String findByNameDoctor(Integer page, String name) {
        return doctorService.findByNameDoctor(name, page);
    }

    @GetMapping("/removeDoctorById")
    @ResponseBody
    public String removeDoctorById(String id) {
        return doctorService.removeDoctorById(id);
    }

    @GetMapping("/batchRemoveDoctor")
    @ResponseBody
    public String batchRemoveDoctor(String ids) {
        return doctorService.batchRemoveDoctor(ids);
    }

    @PostMapping("/addDoctor")
    @ResponseBody
    public String addDoctor(@RequestBody Doctor_information params) {
        return doctorService.addOneDoctor(params);
    }

    @PostMapping("/editDoctor")
    @ResponseBody
    public String editDoctor(@RequestBody Doctor_information params) {
        return doctorService.editDoctor(params);
    }

    @PostMapping("/exportData")
    public void doctorExportData(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\doctor\\Doctor_Information.java";
        ByteArrayOutputStream os = doctorService.doctorExportData(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/dataTemplateDownload")
    public void doctorTemplateDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\doctor\\Doctor_Information.java";
        ByteArrayOutputStream os = doctorService.doctorTemplateDownload(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 接收前端发送过来的数据
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFile = file;
        fileNameNew = UUID.randomUUID().toString();
    }

    /**
     * 向前端传达是否上传成功的信息
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileNameNew + ".xlsx");
        multipartFile.transferTo(newFile);
        Message message = doctorService.analyseFile(fileNameNew);
        return JSON.toJSONString(message);
    }

    @GetMapping("/findByNameList")
    @ResponseBody
    public String findByNameList(String did,String name){
        return  doctorService.findByNameList(did,name);
    }

}
