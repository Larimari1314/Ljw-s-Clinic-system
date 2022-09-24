package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.backend.dao.basic.GeneralMapper;
import sale.ljw.clinicsystem.backend.dao.order.MedicaltechnologyMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorinformationMapper;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnology;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【medicalTechnology】的数据库操作Service实现
 * @createDate 2022-09-21 07:13:48
 */
@Service
public class MedicaltechnologyServiceImpl extends ServiceImpl<MedicaltechnologyMapper, Medicaltechnology>
        implements MedicaltechnologyService {
    @Autowired
    private MedicaltechnologyMapper medicaltechnologyMapper;

    @Autowired
    private DoctorinformationMapper doctorinformationMapper;
    @Autowired
    private GeneralMapper generalMapper;

    @Override
    public String getMedicalProjectNameList() {
        ArrayList<Map<String, Object>> lists = medicaltechnologyMapper.getMedicalProjectNameList();
        return JSON.toJSONString(ResponseResult.getSuccessResult(lists));
    }

    @Override
    public String findMedicalTechnologyList(FindMedicalTechnologyListByPatient medicalTechnology) {
        PageHelper.startPage(medicalTechnology.getPage(), 5);
        ArrayList<Map<String, Object>> medicalTechnologyList = medicaltechnologyMapper.findMedicalTechnologyList(medicalTechnology);
        PageInfo pageInfo = new PageInfo(medicalTechnologyList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public synchronized String findDoctorDutyTimeList(String doctorId) {
        String doctorDutyTime = doctorinformationMapper.findDoctorDutyTime(doctorId);
        //对当前的时间进行判断,如果除了全天班次就返回当前的班次名称
        if(!doctorDutyTime.equals("GT004")){
            return JSON.toJSONString(ResponseResult.getSuccessResult(generalMapper.findDutyTimeByCoding(doctorDutyTime)));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(generalMapper.findDutyTimeByCoding(null)));
    }
}




