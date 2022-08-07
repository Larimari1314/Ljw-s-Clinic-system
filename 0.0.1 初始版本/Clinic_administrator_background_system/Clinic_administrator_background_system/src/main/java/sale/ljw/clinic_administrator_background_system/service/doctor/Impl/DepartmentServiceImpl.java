package sale.ljw.clinic_administrator_background_system.service.doctor.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.doctor.DepartmentDao;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.doctor.DepartmentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public String findAllDepartment() {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "获取数据成功", departmentDao.findAllDepartment());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("获取数据失败", "");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }

    }

    @Override
    public String findById(String id) {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "获取数据成功", departmentDao.findById(id));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("获取数据失败", "");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
