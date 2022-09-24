package sale.ljw.clinicsystem.backend.api.patient.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllHistoryMedicalTechnology;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyreserveService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/medicalTechnologyOrderByPatient")
public class MedicalTechnologyOrderServletByPatient {
    @Autowired
    private MedicaltechnologyreserveService medicaltechnologyreserveService;

    @ApiOperation(value = "获取历史订单")
    @PostMapping("/findAllHistoryMedicalTechnology")
    public String findALlMedicalTechnology(@RequestBody @Valid FindAllHistoryMedicalTechnology historyMedical,HttpServletRequest req){
        return medicaltechnologyreserveService.findAllHistoryMedicalTechnology(historyMedical,req);
    }

    @ApiOperation(value = "查看报告单")
    @GetMapping("/viewTheReport/{id}")
    public String viewTheReport(@PathVariable String id,HttpServletRequest req){
        return medicaltechnologyreserveService.viewTheReport(id,req);
    }
    @ApiOperation(value = "获取全部未支付订单")
    @PostMapping("/findAllUnpaidOrder")
    public String findAllUnpaidOrder(@RequestBody @Valid FindMedicalTechnologyListByPatient medicalTechnology, HttpServletRequest req) {
        return medicaltechnologyreserveService.findAllUnpaidOrder(medicalTechnology,req);
    }

    @ApiOperation(value = "退款")
    @GetMapping("/refundMedicalOrder/{id}")
    public String refundMedicalOrder(@PathVariable String id, HttpServletRequest req){
        return medicaltechnologyreserveService.refundMedicalOrder(id,req);
    }
}
