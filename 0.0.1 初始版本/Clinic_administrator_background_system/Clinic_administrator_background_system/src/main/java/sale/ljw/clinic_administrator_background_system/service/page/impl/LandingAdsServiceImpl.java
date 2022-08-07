package sale.ljw.clinic_administrator_background_system.service.page.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.page.LandingAdsDao;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.page.LandingAdsService;

@Service
public class LandingAdsServiceImpl implements LandingAdsService {
    @Autowired
    private LandingAdsDao landingAdsDao;

    @Override
    public String findALlAds() {
        try {
            WebMessage webMessage=new WebMessage(Code.success_code,"", landingAdsDao.findAllAds());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
            WebMessage webMessage=new WebMessage("", "");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
