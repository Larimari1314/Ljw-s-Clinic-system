package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.basic.DepartmentMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDepartmentByAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Department;
import sale.ljw.clinicsystem.backend.service.basic.DepartmentService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 86155
 * @description 针对表【department】的数据库操作Service实现
 * @createDate 2022-08-07 16:04:27
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {
    /**
     * 获取科室选项数据
     *
     * @return
     */
    @Override
    public String findDepartmentCoding() {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        List<Department> list = list(queryWrapper);
        if (list.size() != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(list), SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String findAllDepartment(FindDepartmentByAdmin department) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(department.getName()), "name", department.getName());
        PageHelper.startPage(department.getPage(), 5);
        List<Department> list = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(list);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String editDepartment(EditDepartmentByAdmin department, MultipartFile multipartFile) {
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\department\\" + department.getId() + ".jpg");
            try {
                multipartFile.transferTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        UpdateWrapper<Department> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", department.getId())
                .set(StringUtils.isNotBlank(department.getName()), "name", department.getName())
                .set(StringUtils.isNotBlank(department.getCover()), "cover", department.getCover())
                .set(StringUtils.isNotBlank(department.getIntroduce()), "introduce", department.getIntroduce());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String addDepartment(AddDepartmentByAdmin department, MultipartFile multipartFile) {
        if(multipartFile==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        Department byId = getById(department.getId());
        if(byId!=null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\department\\" + department.getId() + ".jpg");
        try {
            multipartFile.transferTo(newFile);
            department.setCover("http://localhost:8000/clinic/Img/department/"+department.getId()+".jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Department department1=new Department();
        BeanUtils.copyProperties(department, department1);
        boolean save = save(department1);
        if (save) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




