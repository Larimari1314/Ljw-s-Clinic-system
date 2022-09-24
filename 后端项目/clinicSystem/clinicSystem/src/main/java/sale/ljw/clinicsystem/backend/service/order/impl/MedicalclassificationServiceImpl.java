package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import sale.ljw.clinicsystem.backend.pojo.order.Medicalclassification;
import sale.ljw.clinicsystem.backend.service.order.MedicalclassificationService;
import sale.ljw.clinicsystem.backend.dao.order.MedicalclassificationMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【medicalClassification】的数据库操作Service实现
* @createDate 2022-09-21 07:13:31
*/
@Service
public class MedicalclassificationServiceImpl extends ServiceImpl<MedicalclassificationMapper, Medicalclassification>
    implements MedicalclassificationService{
    @Autowired
    private MedicalclassificationMapper medicalclassificationMapper;
    @Override
    public String getMedicalClassificationList(String id) {
        ArrayList<Map<String, Object>> medicalClassificationList = medicalclassificationMapper.getMedicalClassificationList(id);
        return JSON.toJSONString(ResponseResult.getSuccessResult(medicalClassificationList));
    }
}




