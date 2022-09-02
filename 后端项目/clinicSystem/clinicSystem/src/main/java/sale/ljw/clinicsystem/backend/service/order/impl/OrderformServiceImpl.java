package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import sale.ljw.clinicsystem.backend.form.order.admin.FindOrderFromByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Orderform;
import sale.ljw.clinicsystem.backend.service.order.OrderformService;
import sale.ljw.clinicsystem.backend.dao.order.OrderformMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* @author 86155
* @description 针对表【OrderForm】的数据库操作Service实现
* @createDate 2022-08-07 16:11:50
*/
@Service
public class OrderformServiceImpl extends ServiceImpl<OrderformMapper, Orderform>
    implements OrderformService{
    @Autowired
    private OrderformMapper orderformMapper;
    @Override
    public String findAllOrderFrom(FindOrderFromByAdmin orderFrom) {
        PageHelper.startPage(orderFrom.getPage(),20);
        ArrayList<Map<String, Object>> allOrderFrom = orderformMapper.findAllOrderFrom(orderFrom);
        PageInfo pageInfo=new PageInfo(allOrderFrom);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String findOrderDrugList(String id) {
        ArrayList<Map<String, Object>> list = orderformMapper.findOrderFrom(id);
        if(list!=null){
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String getTotalPrice(String id) {
        Double list = orderformMapper.getOrderTotalPrice(id);
        if(list!=null){
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getSuccessResult(0));
        }
    }

    @Override
    public String invoiceService(String id) {
        //检测是否室已支付状态
        QueryWrapper<Orderform> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id).and(qr->qr.eq("state", "GST02").or().eq("state", "GST03"));
        Long aLong = orderformMapper.selectCount(queryWrapper);
        if(aLong == 0){

            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //将药品列表，医生患者信息和支付信息发送
        Map<String, Object> orderListById = orderformMapper.findOrderListById(id);
        ArrayList<Map<String, Object>> orderFrom = orderformMapper.findOrderFrom(id);
        Double orderTotalPrice = orderformMapper.getOrderTotalPrice(id);
        Map<String,Object> map=new HashMap<>();
        map.put("orderListById", orderListById);
        map.put("drugList", orderFrom);
        map.put("orderTotalPrice", orderTotalPrice);
        return JSON.toJSONString(ResponseResult.getSuccessResult(map),SerializerFeature.DisableCircularReferenceDetect);
    }

}




