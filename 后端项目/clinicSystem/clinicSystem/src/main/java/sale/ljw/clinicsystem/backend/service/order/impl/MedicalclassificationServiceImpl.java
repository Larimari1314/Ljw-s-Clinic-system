package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseStatus;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditMedicalClassificationByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllMedicalClassificationByAdmin;
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

    @Override
    public String findAllMedicalClassification(FindAllMedicalClassificationByAdmin medicalClassification) {
        PageHelper.startPage(medicalClassification.getPage(),10);
        ArrayList<Map<String, Object>> medicalClassificationList=medicalclassificationMapper.findAllMedicalClassification(medicalClassification);
        PageInfo pageInfo=new PageInfo(medicalClassificationList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String editMedicalClassification(EditMedicalClassificationByAdmin medicalClassification) {
        //修改信息
        UpdateWrapper<Medicalclassification> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("id",medicalClassification.getId())
                .set("money", medicalClassification.getMoney())
                .set("maxNumber", medicalClassification.getMaxNumber())
                .set("name", medicalClassification.getName());
        if(!update(updateWrapper_0)){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }
}




