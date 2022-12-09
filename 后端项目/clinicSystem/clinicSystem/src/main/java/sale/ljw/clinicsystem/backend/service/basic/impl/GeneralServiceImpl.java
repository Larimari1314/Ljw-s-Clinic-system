package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.backend.dao.basic.GeneralMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditGeneralByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllGeneralByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.General;
import sale.ljw.clinicsystem.backend.service.basic.GeneralService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【general】的数据库操作Service实现
 * @createDate 2022-08-07 16:06:20
 */
@Service
public class GeneralServiceImpl extends ServiceImpl<GeneralMapper, General>
        implements GeneralService {
    @Autowired
    private GeneralMapper generalMapper;

    /**
     * 获取性别列表
     *
     * @return
     */
    @Override
    public String findSexCoding() {
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coding", "GS001").or().eq("coding", "GS002");
        List<General> list = list(queryWrapper);
        if (list.size() != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findDutyTimeCoding() {
        ArrayList<Map<String, Objects>> dutyTimeCoding = generalMapper.findDutyTimeCoding();
        if (dutyTimeCoding.size() != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(dutyTimeCoding), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findAllByAllDuty() {
        ArrayList<Map<String, Objects>> dutyTimeCoding = generalMapper.selectAllByAllDuty();
        if (dutyTimeCoding.size() != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(dutyTimeCoding), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findPayInformation() {
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("coding", "GST");
        List<General> list = list(queryWrapper);
        if (list.size() != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String getAllGeneral(FindAllGeneralByAdmin general) {
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("value", general.getValue());
        PageHelper.startPage(general.getPage(), 10);
        List<General> list = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(list);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String editGeneral(EditGeneralByAdmin general) {
        UpdateWrapper<General> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("coding", general.getCoding()).set("value", general.getValue());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findDrugSpecification() {
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("coding", "GD");
        List<General> list = list(queryWrapper);
        return JSON.toJSONString(ResponseResult.getSuccessResult(list));
    }
}




