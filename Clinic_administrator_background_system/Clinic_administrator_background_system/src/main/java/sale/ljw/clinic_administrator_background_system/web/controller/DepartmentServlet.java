package sale.ljw.clinic_administrator_background_system.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinic_administrator_background_system.service.doctor.DepartmentService;
import sale.ljw.clinic_administrator_background_system.service.doctor.DoctorInformationService;

@Controller
@RequestMapping("/department")
public class DepartmentServlet {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DoctorInformationService doctorInformationService;

    @GetMapping("/findAllDepartment")
    @ResponseBody
    public String findAllDepartment() {
        return departmentService.findAllDepartment();
    }

    @PostMapping("/findDepartmentDoctor")
    @ResponseBody
    public String findDepartmentDoctor(@RequestBody String params) {
        JSONObject jsonObject = JSON.parseObject(params);
        return doctorInformationService.findDoctorByDepartment(jsonObject.getString("params"));
    }
}
