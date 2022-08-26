package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.clinicsystem.backend.dao.basic.DrugMapper;
import sale.ljw.clinicsystem.backend.dao.order.OrdercompletedMapper;
import sale.ljw.clinicsystem.backend.dao.order.OrderdrugMapper;
import sale.ljw.clinicsystem.backend.dao.order.OrderformMapper;
import sale.ljw.clinicsystem.backend.dao.order.ReserveMapper;
import sale.ljw.clinicsystem.backend.form.basic.doctor.*;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.form.order.doctor.*;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Drug;
import sale.ljw.clinicsystem.backend.pojo.order.Ordercompleted;
import sale.ljw.clinicsystem.backend.pojo.order.Orderdrug;
import sale.ljw.clinicsystem.backend.pojo.order.Orderform;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【reserve】的数据库操作Service实现
 * @createDate 2022-08-07 16:12:15
 */
@Service
public class ReserveServiceImpl extends ServiceImpl<ReserveMapper, Reserve>
        implements ReserveService {
    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private OrderformMapper orderformMapper;
    @Autowired
    private OrderdrugMapper orderdrugMapper;

    @Autowired
    private OrdercompletedMapper ordercompletedMapper;

    @Override
    public String findReserveNotViewed(FindReserveNotViewedByAdmin reserve) {
        PageHelper.startPage(reserve.getPage(), 20);
        ArrayList<Map<String, Object>> reserveNotViewed = reserveMapper.findReserveNotViewed(reserve);
        PageInfo pageInfo = new PageInfo(reserveNotViewed);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String cancelAppointment(DeleteIdsBYAdmin ids) {
        Integer integer = reserveMapper.cancelAppointment(ids.getIds());
        if (integer != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String cancelAppointmentViews(FindReserveNotViewedByAdmin reserve) {
        PageHelper.startPage(reserve.getPage(), 20);
        ArrayList<Map<String, Object>> reserveNotViewed = reserveMapper.cancelAppointmentViews(reserve);
        PageInfo pageInfo = new PageInfo(reserveNotViewed);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String resumeAppointment(DeleteIdsBYAdmin ids) {
        Integer integer = reserveMapper.resumeAppointment(ids.getIds());
        if (integer != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String historyAppointment(FindReserveByDoctor reserve) {
        PageHelper.startPage(reserve.getPage(), 10);
        ArrayList<Map<String, Object>> maps = reserveMapper.historyAppointment(reserve);
        PageInfo pageInfo = new PageInfo(maps);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String checkOrder(CheckOrderByDoctor order) {
        Map<String, Object> map = reserveMapper.checkOrderOnOrderFrom(order);
        if (map == null) {
            //无数据表示：医生的id和订单号对应不上
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(map));
    }

    @Override
    public String checkOrderDrug(String id) {
        ArrayList<Map<String, Object>> map = reserveMapper.checkOrderDrugOnOrderFrom(id);
        if (map == null) {
            //当前无药品
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(map));
    }

    @Override
    public String getAppointmentTime(FindAppointmentTime appointment) {
        //查询当前日期患者是否预约过
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String reserveId = simpleDateFormat.format(appointment.getDate()) + appointment.getPatientId();
        if (reserveMapper.selectById(reserveId) != null) {
            //患者此天预约过
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //将医生当前天数预约的患者全部取出
        ArrayList<Map<String, Object>> appointmentTime = reserveMapper.getAppointmentTime(appointment);
        return JSON.toJSONString(ResponseResult.getSuccessResult(appointmentTime));
    }

    @Override
    public String getOnDuty(String id) {
        return JSON.toJSONString(ResponseResult.getSuccessResult(reserveMapper.getDutyHours(id)));
    }

    @Override
    public String confirmAppointment(ConfirmAppointmentByDoctor confirmAppointment) {
        //首先查询id是否存在
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String resultId = simpleDateFormat.format(confirmAppointment.getReservetime()) + confirmAppointment.getPatientid();
        Reserve result = reserveMapper.selectById(resultId);
        if (result != null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //查询该时间段医生是否已预约
        Map<String, Object> map = reserveMapper.checkAppointmentTime(confirmAppointment);
        if (map != null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //时间段医生未预约则进行预约操作
        Reserve reserve = new Reserve();
        BeanUtils.copyProperties(confirmAppointment, reserve);
        reserve.setId(simpleDateFormat.format(confirmAppointment.getReservetime()) + confirmAppointment.getPatientid());
        boolean save = save(reserve);
        if (save) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findCurrentAppointments(FindReserveByDoctor reserve) {
        PageHelper.startPage(reserve.getPage(), 10);
        ArrayList<Map<String, Object>> maps = reserveMapper.findCurrentAppointments(reserve);
        PageInfo pageInfo = new PageInfo(maps);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String remindPatientNumber(RemindPatientByDoctor remindPatient) {
        if (remindPatient.getNumberreminders() >= 5) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        UpdateWrapper<Reserve> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", remindPatient.getId())
                .set("NumberReminders", remindPatient.getNumberreminders() + 1);
        if (update(updateWrapper)) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String searchMedicinesRemotely(String drugName) {
        QueryWrapper<Drug> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("drugName", drugName).select("id", "drugName")
                .eq("enable", 0)
                .eq("drugDelete", 0);
        List<Drug> drugList = drugMapper.selectList(queryWrapper);
        return JSON.toJSONString(ResponseResult.getSuccessResult(drugList));
    }

    @Override
    public String findDrugByDrugName(OrderFindDrugListByDoctor drugName) {
        PageHelper.startPage(drugName.getPage(), 5);
        QueryWrapper<Drug> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(drugName.getDrugName()), "drugName", drugName.getDrugName())
                .select("id", "drugName", "cover", "number", "price", "enable")
                .eq("enable", 0).eq("drugDelete", 0);
        List<Drug> drugs = drugMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(drugs);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Transactional
    @Override
    public String addDrugByPatient(AddDrugByPatient addDrug) {
        //判断当前预约状态是否是未预约状态
        if (reserveMapper.selectById(addDrug.getOrderId()).getState() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C401"));
        }
        //判断当前药品数量，如果小于当前药品数量，则在原基础上减去药品数量
        Drug drugByDoctor = drugMapper.selectById(addDrug.getDrugId());
        if (drugByDoctor.getNumber() < addDrug.getNumber()) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        drugByDoctor.setNumber(drugByDoctor.getNumber() - addDrug.getNumber());
        if (drugMapper.updateById(drugByDoctor) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //orderForm中添加记录，其他数值记录为默认值即可，状态为：GST01
        //查询当前订单表中是否存在数据，若不存在则创建新的订单id
        Orderform orderform = orderformMapper.selectById(addDrug.getOrderId());
        if (orderform == null) {
            Orderform orderForm = new Orderform();
            orderForm.setId(addDrug.getOrderId());
            orderForm.setOperatingtime(new Date());
            orderForm.setState("GST01");
            int insert = orderformMapper.insert(orderForm);
            if (insert == 0) {
                return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
            }
        } else {
            //若订单表中存在数据,则更新修改时间
            UpdateWrapper<Orderform> updateWrapper = new UpdateWrapper<>();
            orderform.setOperatingtime(new Date());
            updateWrapper.eq("id", orderform.getId())
                    .set("operatingTime", orderform.getOperatingtime());
            int update = orderformMapper.update(orderform, updateWrapper);
            if (update == 0) {
                return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
            }
        }
        //orderDrug中添加药品信息
        //查询当前订单中是否存在相同药品
        QueryWrapper<Orderdrug> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderId", addDrug.getOrderId())
                .eq("drugId", addDrug.getDrugId());
        Orderdrug orderdrug = orderdrugMapper.selectOne(queryWrapper);
        if (orderdrug == null) {
            Orderdrug drug = new Orderdrug();
            drug.setDrugid(addDrug.getDrugId());
            drug.setNumber(addDrug.getNumber());
            drug.setOrderid(addDrug.getOrderId());
            int insert = orderdrugMapper.insert(drug);
            if (insert == 0) {
                return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
            } else {
                return JSON.toJSONString(ResponseResult.getSuccessResult(null));
            }
        } else {
            orderdrug.setNumber(orderdrug.getNumber() + addDrug.getNumber());
            UpdateWrapper<Orderdrug> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("orderId", addDrug.getOrderId())
                    .eq("drugId", addDrug.getDrugId())
                    .set("number", orderdrug.getNumber());
            int update = orderdrugMapper.update(orderdrug, updateWrapper);
            if (update == 0) {
                return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
            } else {
                return JSON.toJSONString(ResponseResult.getSuccessResult(null));
            }
        }
    }
    @Transactional
    @Override
    public String removeDrug(RemoveDrugByDoctor drug) {
        //第一检查订单时候是未预约状态
        if (reserveMapper.selectById(drug.getOrderId()).getState() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C401"));
        }
        //第二步在订单药品表删除药品
        QueryWrapper<Orderdrug> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderId", drug.getOrderId())
                .eq("drugId", drug.getDrugId())
                .eq("number", drug.getNumber());
        int delete = orderdrugMapper.delete(queryWrapper);
        if (delete == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //第三步药品表中药品数量增加
        Drug newDrug = drugMapper.selectById(drug.getDrugId());
        newDrug.setNumber(newDrug.getNumber()+drug.getNumber());
        if (drugMapper.updateById(newDrug) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String endAppointment(String orderId) {
        //检查当前预约状态
        if (reserveMapper.selectById(orderId).getState() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C401"));
        }
        //计算总金额并修改订单表
        Double totalPrice = orderformMapper.getOrderTotalPrice(orderId);
        Integer update = orderformMapper.updateOrderpriceById(orderId, totalPrice);
        if(update==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //修改预约表状态，将state修改成1
        UpdateWrapper<Reserve> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",orderId).set("state", 1);
        if(!update(updateWrapper)){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String findDispensingInterface(FindDispensingInterfaceByDoctor findDispensing) {
        PageHelper.startPage(findDispensing.getPage(),10);
        ArrayList<Map<String, Object>> dispensingInterface = orderformMapper.findDispensingInterface(findDispensing);
        PageInfo pageInfo=new PageInfo(dispensingInterface);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo),SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String dispensingMedicine(DispensingMedicineByDoctor checkOrder) {
        //判断预约状态
        if (reserveMapper.selectById(checkOrder.getReserveId()).getState() != 1) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C401"));
        }
        //查询支付编号和订单id是否对应上
        QueryWrapper<Ordercompleted> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("payment", checkOrder.getPayment())
                        .eq("id",checkOrder.getReserveId());
        if(ordercompletedMapper.selectOne(queryWrapper)==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        //修改状态为订单结束状态
        QueryWrapper<Orderform> queryWrapperOrderFrom=new QueryWrapper<>();
        queryWrapperOrderFrom.eq("id",checkOrder.getReserveId());
        Orderform orderform = orderformMapper.selectOne(queryWrapperOrderFrom);
        orderform.setState("GST03");
        int update = orderformMapper.updateById(orderform);
        if (update == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        } else {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        }
    }

}




