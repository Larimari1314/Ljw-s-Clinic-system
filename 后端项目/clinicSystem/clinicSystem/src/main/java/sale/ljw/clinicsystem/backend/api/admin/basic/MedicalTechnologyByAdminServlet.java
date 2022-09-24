package sale.ljw.clinicsystem.backend.api.admin.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.*;
import sale.ljw.clinicsystem.backend.service.basic.DepartmentService;
import sale.ljw.clinicsystem.backend.service.order.MedicalclassificationService;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/medicalTechnologyByAdmin")
public class MedicalTechnologyByAdminServlet {
    @Autowired
    private MedicaltechnologyService medicaltechnologyService;

    @Autowired
    private DoctorinformationService doctorinformationService;
    @Autowired
    private MedicalclassificationService medicalclassificationService;
    private MultipartFile multipartFile;
    @ApiOperation(value = "获取医技数据")
    @PostMapping("/findAllMedicalTechnology")
    public String findAllMedicalTechnology(@RequestBody @Valid FindAllMedicalTechnologyByAdmin medicalTechnology){
        return medicaltechnologyService.findAllMedicalTechnology(medicalTechnology);
    }
    @ApiOperation(value = "医技封面")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file){
        multipartFile=file;
    }

    @ApiOperation(value = "获取化验科医生列表")
    @GetMapping("/findAllMedicalDoctor")
    public String findAllMedicalDoctor(){
        return doctorinformationService.findAllMedicalDoctor();
    }

    @ApiOperation(value = "编辑医疗技术")
    @PostMapping("/editMedicalTechnology")
    public String editMedicalTechnology(@RequestBody @Valid EditMedicalTechnologyByAdmin medicalTechnology){
        String result = medicaltechnologyService.editMedicalTechnology(medicalTechnology, multipartFile);
        multipartFile=null;
        return result;
    }

    @ApiOperation(value = "查询医疗技术")
    @PostMapping("/findAllMedicalClassification")
    public String findAllMedicalClassification(@RequestBody @Valid FindAllMedicalClassificationByAdmin medicalClassification){
        return medicalclassificationService.findAllMedicalClassification(medicalClassification);
    }

    @ApiOperation(value = "修改医技信息")
    @PostMapping("/editMedicalClassification")
    public String editMedicalClassification(@RequestBody @Valid EditMedicalClassificationByAdmin medicalClassification){
        return medicalclassificationService.editMedicalClassification(medicalClassification);
    }

    @ApiOperation(value = "获取医技预约全部信息")
    @PostMapping("/findMedicalTechnologyOrder")
    public String findMedicalTechnologyOrder(@RequestBody @Valid FindMedicalTechnologyOrderByAdmin medicalTechnologyOrder){
        return medicaltechnologyService.findMedicalTechnologyOrder(medicalTechnologyOrder);
    }
}
