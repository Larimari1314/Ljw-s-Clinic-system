package sale.ljw.clinicsystem.backend.api.doctor.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindBindOrderIdMedicalListByDoctor;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyreserveService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/attendingPhysicianViewMedicalTechnology")
public class ViewTheReportByDoctor {
    @Autowired
    private MedicaltechnologyreserveService medicaltechnologyreserveService;

    @ApiOperation(value = "查看患者关联订单id医疗报告")
    @PostMapping("/findBindOrderIdMedicalList")
    public String findBindOrderIdMedicalList(@RequestBody @Valid FindBindOrderIdMedicalListByDoctor medicalListByDoctor){
        return medicaltechnologyreserveService.findBindOrderIdMedicalList(medicalListByDoctor);
    }

    @ApiOperation(value = "查看患者历史订单医疗报告")
    @GetMapping("/findHistoryBingOrderIdMedicalList/{patientId}")
    public String findHistoryBingOrderIdMedicalList(@PathVariable String patientId){
        return medicaltechnologyreserveService.findHistoryBingOrderIdMedicalList(patientId);
    }
}
