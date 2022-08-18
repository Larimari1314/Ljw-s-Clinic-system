package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.backend.dao.basic.DoctordutyMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDoctorDuty;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditUniteDutyTimeByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import sale.ljw.clinicsystem.backend.service.basic.DoctordutyService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【doctorDuty】的数据库操作Service实现
 * @createDate 2022-08-07 16:05:09
 */
@Service
public class DoctordutyServiceImpl extends ServiceImpl<DoctordutyMapper, Doctorduty>
        implements DoctordutyService {
    @Autowired
    private DoctordutyMapper doctordutyMapper;
    @Autowired
    private DoctorinformationService doctorinformationService;

    @Override
    public String findAllDoctor(FindDoctorByAdmin doctor) {
        PageHelper.startPage(doctor.getPage(), 20);
        ArrayList<Map<String, Objects>> allDoctor = doctordutyMapper.findAllSchedulesDoctor(doctor);
        PageInfo pageInfo = new PageInfo(allDoctor);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String editDoctorDuty(EditDoctorDuty doctorDuty) {
        UpdateWrapper<Doctorduty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", doctorDuty.getId())
                .set("dutyId", doctorDuty.getScheduledCoding());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findDutyTime() {
        ArrayList<Map<String, Objects>> dutyTime = doctordutyMapper.findDutyTime();
        return JSON.toJSONString(ResponseResult.getSuccessResult(dutyTime));
    }

    @Override
    public String editUniteDoctorDutyTime(EditUniteDutyTimeByAdmin dutyTime) {
        QueryWrapper<Doctorinformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(dutyTime.getSex()),"sex", dutyTime.getSex())
                .eq(StringUtils.isNotBlank(dutyTime.getDepartment()),"did", dutyTime.getDepartment())
                .eq(StringUtils.isNotBlank(dutyTime.getRegistered()),"registereId", dutyTime.getRegistered())
                .between("age", dutyTime.getStartAge(), dutyTime.getEndAge())
                .select("id");
        Map<String, Object> map = doctorinformationService.getMap(queryWrapper);
        UpdateWrapper<Doctorduty> updateWrapper=new UpdateWrapper<>();
        updateWrapper.in("id", map.values())
                        .set("dutyId", dutyTime.getScheduleRule());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




