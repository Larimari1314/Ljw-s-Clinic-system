package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.backend.dao.basic.DoctordutyMapper;
import sale.ljw.clinicsystem.backend.dao.basic.GeneralMapper;
import sale.ljw.clinicsystem.backend.dao.basic.MedicaldoctorMapper;
import sale.ljw.clinicsystem.backend.dao.order.MedicalclassificationMapper;
import sale.ljw.clinicsystem.backend.dao.order.MedicaltechnologyreserveMapper;
import sale.ljw.clinicsystem.backend.form.basic.doctor.EndOfVisitByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.doctor.FindBindOrderIdMedicalListByDoctor;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindAllHistoryMedicalTechnology;
import sale.ljw.clinicsystem.backend.form.basic.patient.FindMedicalTechnologyListByPatient;
import sale.ljw.clinicsystem.backend.form.basic.patient.MedicalTechnologyAppointmentForm;
import sale.ljw.clinicsystem.backend.form.order.doctor.FindAllReserveMedicalTechnology;
import sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty;
import sale.ljw.clinicsystem.backend.pojo.basic.General;
import sale.ljw.clinicsystem.backend.pojo.order.Medicalclassification;
import sale.ljw.clinicsystem.backend.pojo.order.Medicaltechnologyreserve;
import sale.ljw.clinicsystem.backend.service.order.MedicaltechnologyreserveService;
import sale.ljw.clinicsystem.common.http.ResponseResult;
import sale.ljw.clinicsystem.common.sercurity.utils.JwtUtils;
import sale.ljw.clinicsystem.common.sercurity.utils.QRcodeZxingUtil2;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 86155
 * @description 针对表【medicalTechnologyReserve】的数据库操作Service实现
 * @createDate 2022-09-21 07:13:51
 */
@Service
public class MedicaltechnologyreserveServiceImpl extends ServiceImpl<MedicaltechnologyreserveMapper, Medicaltechnologyreserve>
        implements MedicaltechnologyreserveService {
    @Autowired
    private MedicaltechnologyreserveMapper medicaltechnologyreserveMapper;
    @Autowired
    private MedicalclassificationMapper medicalclassificationMapper;

    @Autowired
    private MedicaldoctorMapper medicaldoctorMapper;

    @Autowired
    private DoctordutyMapper doctordutyMapper;
    @Autowired
    private GeneralMapper generalMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //记录当前循环次数
    private Integer resultNumber = 0;
    private Boolean systemChanges = false;

    @Override
    public String findCurrentAppointmentMedicalTechnology(HttpServletRequest req) {
        //根据token获取医生id
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        //获取当前已付费用户姓名(GST02)
        PageHelper.startPage(1, 20);
        List<Map<String, Object>> medicaltechnologyList = medicaltechnologyreserveMapper.findCurrentAppointmentMedicalTechnology(doctorId, "GST02");
        //返回数据
        PageInfo pageInfo = new PageInfo(medicaltechnologyList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String getAppointmentNumber(HttpServletRequest req) {
        //根据token获取医生id
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        //获取当前已付费用户姓名(GST02)
        Map<String, Object> appointmentNumber = medicaltechnologyreserveMapper.getAppointmentNumber(doctorId);
        return JSON.toJSONString(ResponseResult.getSuccessResult(appointmentNumber));
    }

    @Override
    public String findAllReserveMedicalTechnology(FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req) {
        //获取医生id
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        //查询当前预约记录
        PageHelper.startPage(medicalTechnology.getPage(), 15);
        List<Map<String, Object>> allReserveMedicalTechnology = medicaltechnologyreserveMapper.findAllReserveMedicalTechnology(medicalTechnology, doctorId);
        PageInfo pageInfo = new PageInfo(allReserveMedicalTechnology);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String judgmentStatusPaid(String id) {
        //判断当前状态是否是已支付状态
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST02");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String findDiagnoseReportData(String id, HttpServletRequest req) {
        //查询当前报告id是否存在且是否是已支付状态
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST02");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        Map<String, Object> diagnoseReportData = medicaltechnologyreserveMapper.findDiagnoseReportData(id);
        return JSON.toJSONString(ResponseResult.getSuccessResult(diagnoseReportData));
    }

    @Override
    public String cancelAppointment(String id, HttpServletRequest req) {
        //判断当前预约是否是未支付
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("doctorId", doctorId).eq("status", "GST01");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //验证通过更新字段
        UpdateWrapper<Medicaltechnologyreserve> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("id", id).set("status", "GST04");
        if (!update(updateWrapper_0)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C203"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String findHistoricalAppointments(FindAllReserveMedicalTechnology medicalTechnology, HttpServletRequest req) {
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        //查询当前预约记录
        PageHelper.startPage(medicalTechnology.getPage(), 15);
        List<Map<String, Object>> allReserveMedicalTechnology = medicaltechnologyreserveMapper.findHistoricalAppointments(medicalTechnology, doctorId);
        PageInfo pageInfo = new PageInfo(allReserveMedicalTechnology);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String queryBindingAppointment(HttpServletRequest req) {
        //获取患者id查询患者旗下的预约记录
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        List<Map<String, Object>> maps = medicaltechnologyreserveMapper.queryBindingAppointment(patientId);
        if (maps.size() == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(maps));
    }

    @Override
    public String medicalTechnologyAppointment(MedicalTechnologyAppointmentForm medicalTechnology, HttpServletRequest req) {
        //获取用户id
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        String id = null;
        if (medicalTechnology.getReserveid() != null) {
            id = medicalTechnology.getReserveid() + medicalTechnology.getMedicalid();
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            id = simpleDateFormat.format(medicalTechnology.getReservedate()) + medicalTechnology.getMedicalid();
        }
        //检测当前订单是否存在，若存在则返回403消息码
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_3 = new QueryWrapper<>();
        queryWrapper_3.eq("id", id);
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_3) != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C203"));
        }
        //查询当前常规项时间段最大人数
        QueryWrapper<Medicalclassification> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", medicalTechnology.getMedicalid()).select("maxNumber");
        Integer maxNumber = medicalclassificationMapper.selectOne(queryWrapper_0).getMaxnumber();
        //查询医生是否负责该医技
        if (medicaldoctorMapper.findMedicalResultCount(medicalTechnology) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //查询当前医生此时间段的预约人数，为用户分配编号
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("reserveDate", medicalTechnology.getReservedate())
                .eq("reserveTime", medicalTechnology.getReservetime())
                .eq("doctorId", medicalTechnology.getDoctorid())
                .eq("medicalId", medicalTechnology.getMedicalid());
        Long aLong = medicaltechnologyreserveMapper.selectCount(queryWrapper_1);
        if (aLong >= maxNumber) {
            //如果时间段人数超过个数则查询医生当天是否存在其他时间段，如果不存在则弹出告知用户医生当天预约已满
            //查询医生当前值班id是否是全天班次
            if (resultNumber <= 3) {
                QueryWrapper<Doctorduty> queryWrapper_2 = new QueryWrapper<>();
                queryWrapper_2.eq("id", medicalTechnology.getDoctorid()).select("dutyId");
                if (!doctordutyMapper.selectOne(queryWrapper_2).getDutyid().equals("GT004")) {
                    return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
                }
                //当前医生班次为全天班次，则检查选择的班次
                //存在其他时间段则将上述过程递归，知道找出时间才可
                if (medicalTechnology.getReservetime().equals("GT001")) {
                    medicalTechnology.setReservetime("GT002");
                } else if (medicalTechnology.getReservetime().equals("GT002")) {
                    medicalTechnology.setReservetime("GT003");
                } else if (medicalTechnology.getReservetime().equals("GT003")) {
                    medicalTechnology.setReservetime("GT001");
                }
                //证明当前医生记录已由系统发生改变,标记
                systemChanges = true;
                resultNumber++;
                //递归
                return medicalTechnologyAppointment(medicalTechnology, req);
            } else {
                resultNumber = 0;
                systemChanges = false;
                return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
            }

        }
        //查询班次编码对应的value
        QueryWrapper<General> queryWrapper_4 = new QueryWrapper<>();
        queryWrapper_4.eq("coding", medicalTechnology.getReservetime()).select("value");
        General generalTime = generalMapper.selectOne(queryWrapper_4);
        Integer result = Math.toIntExact(aLong + 1);
        Medicaltechnologyreserve medicaltechnologyreserve = new Medicaltechnologyreserve();
        BeanUtils.copyProperties(medicalTechnology, medicaltechnologyreserve);
        //id构成为若存在订单id则将订单id+检测项目作为id，若不存在订单id则当前日期戳为id
        medicaltechnologyreserve.setId(id);
        medicaltechnologyreserve.setStatus("GST01");
        medicaltechnologyreserve.setPatientid(patientId);
        medicaltechnologyreserve.setQueuenumber(result);
        medicaltechnologyreserve.setAppointmenttime(new Date());
        //封装结束，添加预约记录
        if (medicaltechnologyreserveMapper.insert(medicaltechnologyreserve) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        Map<String, Object> maps = new HashedMap<>();
        maps.put("queueNumber", result);
        maps.put("id", id);
        //查询当前记录是否发生改变
        if (systemChanges) {
            //发生改变
            maps.put("systemChanges", generalTime.getValue());
        } else {
            maps.put("noSystemChanges", generalTime.getValue());
        }
        resultNumber = 0;
        systemChanges = false;
        return JSON.toJSONString(ResponseResult.getSuccessResult(maps));
    }

    @Override
    public String confirmPayment(String id) {
        Map<String, Object> map = medicaltechnologyreserveMapper.confirmPayment(id);
        return JSON.toJSONString(ResponseResult.getSuccessResult(map));
    }

    @Override
    public String payingEncryption(String id) {
        //查询订单是否是未支付状态
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST01");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            //支付订单不存在或者状态异常
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //通过验证，将30位编码和当前订单进行绑定
        //绑定支付id和用户id
        String s = UUID.randomUUID().toString();
        s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        String substring = s.substring(0, 30);
        //绑定
        redisTemplate.boundValueOps(substring).set(id, 5, TimeUnit.MINUTES);
        return JSON.toJSONString(ResponseResult.getSuccessResult(substring));
    }

    @Override
    public String queryPaymentInformation(String payingEncryptionId) {
        String id = (String) redisTemplate.boundValueOps(payingEncryptionId).get();
        if (id == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST01");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            //支付订单不存在或者状态异常
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //支付信息
        return confirmPayment(id);
    }

    @Override
    public String getQRCodeMedical(String id) {
        try {
            return JSON.toJSONString(ResponseResult.getSuccessResult(QRcodeZxingUtil2.getQRCodeImage("http://192.168.1.106:3088/#/payingMedical/" + id)));
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String scanPayStatusMedical(String id) {
        String ids = (String) redisTemplate.boundValueOps(id).get();
        if (ids == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        redisTemplate.boundValueOps(id + ids).set("pay201", 5, TimeUnit.MINUTES);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    //加锁
    @Override
    public synchronized String payingMedical(String payingEncryptionId) {
        //检测订单状态
        String id = (String) redisTemplate.boundValueOps(payingEncryptionId).get();
        if (id == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //更新订单状态
        //查询订单是否是未支付状态
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).select("status", "id");
        Medicaltechnologyreserve medicaltechnologyreserve = medicaltechnologyreserveMapper.selectOne(queryWrapper_0);
        if (medicaltechnologyreserve == null) {
            //支付订单不存在或者状态异常
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        if (medicaltechnologyreserve.getStatus().equals("GST02")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        UpdateWrapper<Medicaltechnologyreserve> updateWrapper_0 = new UpdateWrapper<>();
        //更新价格
        //获取价格
        Double totalPrice = medicaltechnologyreserveMapper.getTotalPrice(id);
        updateWrapper_0.eq("id", id).set("status", "GST02").set("money", totalPrice);
        if (!update(updateWrapper_0)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        redisTemplate.boundValueOps(payingEncryptionId + id).set("pay200", 5, TimeUnit.MINUTES);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String findAllUnpaidOrder(FindMedicalTechnologyListByPatient medicalTechnology, HttpServletRequest req) {
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        PageHelper.startPage(medicalTechnology.getPage(), 5);
        List<Map<String, Object>> medicalTechnologyList = medicaltechnologyreserveMapper.findAllUnpaidOrder(medicalTechnology, patientId);
        PageInfo pageInfo = new PageInfo(medicalTechnologyList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public synchronized String refundMedicalOrder(String id, HttpServletRequest req) {
        //查询订单状态
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("patientId", patientId).select("status");
        if (!medicaltechnologyreserveMapper.selectOne(queryWrapper_0).getStatus().equals("GST02")) {
            //订单状态异常
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        UpdateWrapper<Medicaltechnologyreserve> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("id", id).eq("patientId", patientId).set("status", "GST04").set("id", id + new Date().getTime());
        if (!update(updateWrapper_0)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String endOfVisit(EndOfVisitByDoctor endOfVisitByDoctor, HttpServletRequest req) {
        //检查订单id状态
        String doctorId = JwtUtils.parseJWT(req.getHeader("token"));
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", endOfVisitByDoctor.getId()).eq("doctorId", doctorId).select("status");
        Medicaltechnologyreserve medicaltechnologyreserve = medicaltechnologyreserveMapper.selectOne(queryWrapper_0);
        if(medicaltechnologyreserve==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        if(!medicaltechnologyreserve.getStatus().equals("GST02")){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        UpdateWrapper<Medicaltechnologyreserve> updateWrapper_0=new UpdateWrapper<>();
        updateWrapper_0.eq("id", endOfVisitByDoctor.getId())
                        .set("status", "GST03")
                        .set("checkParts", endOfVisitByDoctor.getCheckParts())
                        .set("inspectionReport", endOfVisitByDoctor.getInspectionReport());
        if(!update(updateWrapper_0)){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String viewReport(String id,HttpServletRequest req) {
        //查询当前报告id是否存在且是否是已支付状态
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST03");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        Map<String, Object> diagnoseReportData = medicaltechnologyreserveMapper.findDiagnoseReportData(id);
        return JSON.toJSONString(ResponseResult.getSuccessResult(diagnoseReportData));
    }

    @Override
    public String findAllHistoryMedicalTechnology(FindAllHistoryMedicalTechnology historyMedical, HttpServletRequest req) {
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        PageHelper.startPage(historyMedical.getPage(),10);
        ArrayList<Map<String,Object>> medicalList=medicaltechnologyreserveMapper.findAllHistoryMedicalTechnology(historyMedical,patientId);
        PageInfo pageInfo=new PageInfo(medicalList);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String viewTheReport(String id, HttpServletRequest req) {
        //查询当前报告id是否存在且是否是已支付状态
        String patientId = JwtUtils.parseJWT(req.getHeader("token"));
        QueryWrapper<Medicaltechnologyreserve> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("id", id).eq("status", "GST03");
        if (medicaltechnologyreserveMapper.selectCount(queryWrapper_0) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        Map<String, Object> diagnoseReportDataByPatient = medicaltechnologyreserveMapper.findDiagnoseReportDataByPatient(id, patientId);
        return JSON.toJSONString(ResponseResult.getSuccessResult(diagnoseReportDataByPatient));
    }

    @Override
    public String findBindOrderIdMedicalList(FindBindOrderIdMedicalListByDoctor medicalListByDoctor) {
        //获取历史报告
        List<Map<String, Object>> bindOrderIdMedicalList = medicaltechnologyreserveMapper.findBindOrderIdMedicalList(medicalListByDoctor);
        if (bindOrderIdMedicalList.size()==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(bindOrderIdMedicalList));
    }

    @Override
    public String findHistoryBingOrderIdMedicalList(String patientId) {
        List<Map<String, Object>> historyBingOrderIdMedicalList = medicaltechnologyreserveMapper.findHistoryBingOrderIdMedicalList(patientId);
        if (historyBingOrderIdMedicalList.size()==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(historyBingOrderIdMedicalList));
    }


}




