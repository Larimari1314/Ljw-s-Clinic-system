package sale.ljw.clinicsystem.backend.api.admin.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/medicalTechnology")
public class MedicalTechnologyServlet {
    @ApiOperation(value = "获取医技数据")
    @PostMapping("/findAllMedicalTechnology")
    public String findAllMedicalTechnology(@RequestBody @Valid ){
        
    }
}
