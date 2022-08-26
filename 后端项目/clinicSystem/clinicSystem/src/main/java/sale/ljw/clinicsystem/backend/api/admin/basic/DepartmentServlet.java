package sale.ljw.clinicsystem.backend.api.admin.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.service.basic.DepartmentService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentServlet {
    @Autowired
    private DepartmentService departmentService;
    private MultipartFile multipartFile;

    @GetMapping("/findDepartmentCoding")
    @ApiOperation(value = "获取科室列表")
    public String findDepartmentCoding() {
        return departmentService.findDepartmentCoding();
    }

    @PostMapping("/findAllDepartment")
    @ApiOperation("查找科室")
    public String findALlDepartment(@RequestBody @Valid FindDepartmentByAdmin department) {
        return departmentService.findAllDepartment(department);
    }

    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "编辑科室信息")
    @PostMapping("/editDepartment")
    public String editDepartment(@RequestBody @Valid EditDepartmentByAdmin department) {
        String result = departmentService.editDepartment(department, multipartFile);
        multipartFile=null;
        return result;
    }

    @ApiOperation(value = "添加科室信息")
    @PostMapping("/addDepartment")
    public String addDepartment(@RequestBody @Valid AddDepartmentByAdmin department){
        String result = departmentService.addDepartment(department, multipartFile);
        multipartFile=null;
        return result;
    }
}
