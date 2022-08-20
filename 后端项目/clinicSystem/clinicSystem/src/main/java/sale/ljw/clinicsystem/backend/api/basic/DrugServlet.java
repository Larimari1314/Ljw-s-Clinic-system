package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.DrugSwitchingByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.service.basic.DrugService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/drug")
public class DrugServlet {
    @Autowired
    private DrugService drugService;
    private MultipartFile multipartFile;

    @ApiOperation(value = "查找全部药品")
    @PostMapping("/findAllDrug")
    public String findAllDrug(@RequestBody @Valid FindDrugByAdmin drug) {
        return drugService.findAllDrug(drug);
    }

    @ApiOperation(value = "药品封面上传暴漏端口")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "开关设置")
    @PostMapping("/switching")
    public String switching(@RequestBody @Valid DrugSwitchingByAdmin switching) {
        return drugService.switching(switching);
    }

    @ApiOperation(value = "更新药品信息")
    @PostMapping("/editDrug")
    public String editDrug(@RequestBody @Valid EditDrugByAdmin drug) {
        String result = drugService.editDrug(drug, multipartFile);
        multipartFile = null;
        return result;
    }

    @ApiOperation(value = "逻辑删除药品")
    @PostMapping("/deleteDrugByIds")
    public String deleteDrug(@RequestBody @Valid DeleteIdsBYAdmin ids){
        return drugService.deleteDrugByIds(ids);
    }

    @ApiOperation(value = "添加药品")
    @PostMapping("/addDrug")
    public String addDrug(@RequestBody @Valid AddDrugByAdmin drug){
        return drugService.addDrug(drug,multipartFile);
    }
    @ApiOperation(value = "下载数据模板")
    @PostMapping("/dataTemplateDownload")
    public void userTemplateDownload(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = drugService.drugTemplateDownload(response);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "下载全部数据")
    @PostMapping("/exportData")
    public void exportDataUser(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = drugService.exportData(response);
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "上传excel")
    @PostMapping("/uploadToServer")
    public void uploadToServer(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "导入数据")
    @GetMapping("/analyseFile")
    @ResponseBody
    public String analyseFile() throws IOException {
        return drugService.analyseFile(multipartFile);
    }
    @ApiOperation("获取删除数据")
    @PostMapping("/getDeleteData")
    public String getDeleteData(){
        return drugService.getDeleteData();
    }

    @ApiOperation(value = "永久删除数据")
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable String id) {
        return drugService.deleteById(id);
    }

    @ApiOperation(value = "恢复恢复数据")
    @GetMapping("/recoveryData/{id}")
    public String recoveryData(@PathVariable String id){
        return drugService.recoveryData(id);
    }

}
