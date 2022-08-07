package sale.ljw.clinic_administrator_background_system.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.Drug_information;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.DrugInformationService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
@RequestMapping("/drug")
public class DrugServlet {
    private static String fileNameNew = null;
    private static MultipartFile multipartFile;
    @Autowired
    private DrugInformationService drugInformationService;

    @GetMapping("/findAllDrug")
    @ResponseBody
    public String findAllDoctorTest(Integer page) {
        return drugInformationService.findAllDrug(page);
    }

    @GetMapping("/findByNameDrug")
    @ResponseBody
    public String findByNameDoctor(Integer page, String name) {
        return drugInformationService.findByNameDrug(page, name);
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

    @PostMapping("/addDrug")
    @ResponseBody
    public String addDoctor(@RequestBody Drug_information params) throws IOException {
        String path = System.getProperty("user.dir");
        String id = UUID.randomUUID().toString();
        params.setId(id);
        String pathname = path + "\\src\\main\\webapp\\img\\drug\\" + params.getId();
        File newFile = new File(pathname);
        if (newFile.mkdirs()) {
            File File = new File(pathname + "\\" + id + ".jpg");
            multipartFile.transferTo(File);
            return drugInformationService.addOneDrug(params, id);
        } else {
            WebMessage webMessage = new WebMessage("添加失败", "");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @PostMapping("/editDrug")
    @ResponseBody
    public String editDoctor(@RequestBody Drug_information params) {
        return drugInformationService.editDrug(params);
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\clinic\\Drug_information.java";
        ByteArrayOutputStream os = drugInformationService.DrugExportData(url);
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\clinic\\Drug_information.java";
        ByteArrayOutputStream os = drugInformationService.DrugTemplateDownload(url);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
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
        Message message = drugInformationService.analyseFile(fileNameNew);
        return JSON.toJSONString(message);
    }

    @PostMapping("/exitDrugExhibit")
    @ResponseBody
    public String exitDrugExhibit(@RequestBody String params) {
        Drug_information drug_information = JSONObject.parseObject(params, Drug_information.class);
        return drugInformationService.exitDrugExhibit(drug_information);
    }
}
