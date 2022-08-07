package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.clinic.RegistrationInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.RegistrationInformationService;

@Service
public class RegistrationInformationServiceImpl implements RegistrationInformationService {
    @Autowired
    private RegistrationInformationDao registrationInformationDao;

    @Override
    public String findById(String id) {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "", registrationInformationDao.findById(id));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            WebMessage webMessage = new WebMessage("查找失败，请稍后重试", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
