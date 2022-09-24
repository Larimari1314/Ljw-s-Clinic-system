package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.basic.GeneralMapper;
import sale.ljw.clinicsystem.backend.dao.basic.MedicaldoctorMapper;
import sale.ljw.clinicsystem.backend.dao.order.MedicaltechnologyMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorinformationMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.DisplayDataMedicalTechnologyByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditMedicalTechnologyByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllMedicalTechnologyByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindMedicalTechnologyOrderByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.pojo.basic.Medicaldoctor;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnology;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private MedicaldoctorMapper medicaldoctorMapper;
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
        if (!doctorDutyTime.equals("GT004")) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(generalMapper.findDutyTimeByCoding(doctorDutyTime)));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(generalMapper.findDutyTimeByCoding(null)));
    }

    @Override
    public String findAllMedicalTechnology(FindAllMedicalTechnologyByAdmin medicalTechnology) {
        PageHelper.startPage(medicalTechnology.getPage(), 10);
        ArrayList<DisplayDataMedicalTechnologyByAdmin> medicalTechnologyByAdmins = medicaltechnologyMapper.findAllMedicalTechnology(medicalTechnology);
        PageInfo pageInfo = new PageInfo(medicalTechnologyByAdmins);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Transactional
    @Override
    public String editMedicalTechnology(EditMedicalTechnologyByAdmin medicalTechnology, MultipartFile multipartFile) {
        //判断当前上传参数是否为空
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\medicalTechnology\\" + medicalTechnology.getId() + ".jpg");
            try {
                multipartFile.transferTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //保存数据基本数据
        UpdateWrapper<Medicaltechnology> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("id", medicalTechnology.getId()).set("projectName", medicalTechnology.getMedicalId()).set("technologyname", medicalTechnology.getMedicalName());
        if (!update(updateWrapper_0)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //保存医生列表数据
        QueryWrapper<Medicaldoctor> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("medicalId", medicalTechnology.getId()).select("doctorId");
        ArrayList<String> newModuleIds = medicalTechnology.getIds();
        List<String> oldModuleIds = medicaldoctorMapper.selectList(queryWrapper_0).stream().map(Medicaldoctor::getDoctorid).collect(Collectors.toList());
        int m = 0, i = 0;
        while (true) {
            if (oldModuleIds.size() == 0 || newModuleIds.size() == 0 || m >= oldModuleIds.size()) {
                break;
            }
            if (Objects.equals(oldModuleIds.get(m), newModuleIds.get(i))) {
                newModuleIds.remove(newModuleIds.get(i));
                oldModuleIds.remove(oldModuleIds.get(m));
            } else {
                i++;
            }
            if (i >= newModuleIds.size()) {
                i = 0;
                m++;
            }
        }
        for (int j = 0; j < oldModuleIds.size(); j++) {
            QueryWrapper<Medicaldoctor> queryWrapper_1 = new QueryWrapper<>();
            queryWrapper_1.eq("medicalId", medicalTechnology.getId()).eq("doctorId", oldModuleIds.get(j));
            if (medicaldoctorMapper.delete(queryWrapper_1) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
            }
        }
        for (int q = 0; q < newModuleIds.size(); q++) {
            Medicaldoctor doctor = new Medicaldoctor();
            doctor.setMedicalid(medicalTechnology.getId());
            doctor.setDoctorid(newModuleIds.get(q));
            if (medicaldoctorMapper.insert(doctor) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
            }
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String findMedicalTechnologyOrder(FindMedicalTechnologyOrderByAdmin medicalTechnologyOrder) {
        PageHelper.startPage(medicalTechnologyOrder.getPage(), 10);
        ArrayList<Map<String, Object>> medicalTechnologyList = medicaltechnologyMapper.findMedicalTechnologyOrder(medicalTechnologyOrder);
        PageInfo pageInfo=new PageInfo(medicalTechnologyList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }
}





