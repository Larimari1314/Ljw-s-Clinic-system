package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AppointmentTimeNumberDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTimeNumber;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.AppointmentTimeNumberService;

import java.util.ArrayList;

@Service
public class AppointmentTimeNumberServiceImpl implements AppointmentTimeNumberService {
    @Autowired
    private AppointmentTimeNumberDao appointmentTimeNumberDao;

    @Override
    public String findAll(Integer page) {
        try {
            PageHelper.startPage(page, 20);
            ArrayList<AppointmentTimeNumber> allTime = appointmentTimeNumberDao.findAll();
            PageInfo pageInfo = new PageInfo(allTime);
            WebMessage webMessage = new WebMessage(Code.success_code, "查找数据成功", pageInfo);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            WebMessage webMessage = new WebMessage("查找失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String exitNumber(String id,String text) {
        AppointmentTimeNumber AppointmentNumber = appointmentTimeNumberDao.findById(id);
        String timeNumber = AppointmentNumber.getTimeNumber();
        if(text.equals("morning")){
            if(timeNumber.equals("1")){
                AppointmentNumber.setTimeNumber("4");
            } else if (timeNumber.equals("2")) {
                AppointmentNumber.setTimeNumber("3");
            } else if (timeNumber.equals("3")) {
                AppointmentNumber.setTimeNumber("2");
            } else if (timeNumber.equals("4")) {
                AppointmentNumber.setTimeNumber("1");
            }
        } else if (text.equals("afternoon")) {
            if(timeNumber.equals("1")){
                AppointmentNumber.setTimeNumber("3");
            } else if (timeNumber.equals("2")) {
                AppointmentNumber.setTimeNumber("4");
            } else if (timeNumber.equals("3")) {
                AppointmentNumber.setTimeNumber("1");
            } else if (timeNumber.equals("4")) {
                AppointmentNumber.setTimeNumber("2");
            }
        }
       if(appointmentTimeNumberDao.exitNumber(AppointmentNumber)==1){
           return JSON.toJSONString(new WebMessage(Code.success_code,"修改成功",""), SerializerFeature.DisableCircularReferenceDetect);
       }else {
           return JSON.toJSONString(new WebMessage("修改失败",""),SerializerFeature.DisableCircularReferenceDetect);
       }
    }
}
