package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import sale.ljw.clinicsystem.backend.pojo.basic.Department;
import sale.ljw.clinicsystem.backend.service.basic.DepartmentService;
import sale.ljw.clinicsystem.backend.dao.basic.DepartmentMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.util.List;

/**
* @author 86155
* @description 针对表【department】的数据库操作Service实现
* @createDate 2022-08-07 16:04:27
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{
    /**
     * 获取科室选项数据
     * @return
     */
    @Override
    public String findDepartmentCoding() {
        QueryWrapper<Department> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id","name");
        List<Department> list = list(queryWrapper);
        if(list.size()!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




