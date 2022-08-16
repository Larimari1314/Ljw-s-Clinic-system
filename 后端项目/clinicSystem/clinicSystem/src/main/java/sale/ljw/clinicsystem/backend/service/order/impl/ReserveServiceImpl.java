package sale.ljw.clinicsystem.backend.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.backend.dao.order.ReserveMapper;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.ArrayList;
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
}




