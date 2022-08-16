package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.clinicsystem.backend.service.basic.DepartmentService;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentServlet {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/findDepartmentCoding")
    @ApiOperation(value = "获取科室列表")
    public String findDepartmentCoding(){
        return departmentService.findDepartmentCoding();
    }
}
