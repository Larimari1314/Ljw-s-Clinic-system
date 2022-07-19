package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AdminInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminInformation;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.AdminInformationService;

@Service
public class AdminInformationServiceImpl implements AdminInformationService {
    @Autowired
    private AdminInformationDao adminInformationDao;

    @Override
    public String editAdmin(AdminInformation adminInformation) {
        try {
            Integer integer = adminInformationDao.editAdmin(adminInformation);
            WebMessage webMessage=new WebMessage();
            if (integer!=0) {
                webMessage.setCode(Code.success_code);
                webMessage.setMessage("修改成功，返回登陆界面");
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
            }else {
                webMessage.setMessage("修改失败,请稍后重试");
                webMessage.setCode(Code.server_error_code);
                return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
            }
        }catch (Exception e){
            e.printStackTrace();
            WebMessage webMessage=new WebMessage("出错了，请稍后重试",e.getMessage());
            return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
