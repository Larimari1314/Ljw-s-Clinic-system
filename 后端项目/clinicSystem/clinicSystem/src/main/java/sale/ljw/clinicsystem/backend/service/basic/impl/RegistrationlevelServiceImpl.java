package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditRegistrationLevelByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Registrationlevel;
import sale.ljw.clinicsystem.backend.service.basic.RegistrationlevelService;
import sale.ljw.clinicsystem.backend.dao.basic.RegistrationlevelMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【registrationLevel】的数据库操作Service实现
* @createDate 2022-08-07 16:07:34
*/
@Service
public class RegistrationlevelServiceImpl extends ServiceImpl<RegistrationlevelMapper, Registrationlevel>
    implements RegistrationlevelService{
    @Autowired
    private RegistrationlevelMapper registrationlevelMapper;
    @Override
    public String findAllRegistrationLevel() {
        PageHelper.startPage(1,10);
        ArrayList<Map<String, Object>> allRegistrationLevel = registrationlevelMapper.findAllRegistrationLevel();
        PageInfo pageInfo=new PageInfo(allRegistrationLevel);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String editRegistrationLevel(EditRegistrationLevelByAdmin registrationLevel) {
        UpdateWrapper<Registrationlevel> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", registrationLevel.getId())
                        .set("price",registrationLevel.getPrice());
        boolean update = update(updateWrapper);
        if(update){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




