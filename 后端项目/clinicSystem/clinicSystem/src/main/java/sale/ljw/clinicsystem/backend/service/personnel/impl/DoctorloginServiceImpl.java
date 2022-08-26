package sale.ljw.clinicsystem.backend.service.personnel.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorinformationMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.form.personnel.doctor.EditDoctorLoginByDoctor;
import sale.ljw.clinicsystem.backend.pojo.personnel.Adminlogin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorloginService;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorloginMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
* @author 86155
* @description 针对表【doctorLogin】的数据库操作Service实现
* @createDate 2022-08-07 16:01:25
*/
@Service
public class DoctorloginServiceImpl extends ServiceImpl<DoctorloginMapper, Doctorlogin>
    implements DoctorloginService{
    @Autowired
    private DoctorloginMapper doctorloginMapper;
    @Autowired
    private DoctorinformationMapper doctorinformationMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public String login(LoginFrom login, HttpServletResponse response) {
        QueryWrapper<Doctorlogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(login.getPassword().getBytes(StandardCharsets.UTF_8)))
                .eq("LoginName", login.getLoginCredentials())
                .or().eq("LoginPhone", login.getLoginCredentials())
                .or().eq("LoginEmail", login.getLoginCredentials());
        Doctorlogin doctorlogin = doctorloginMapper.selectOne(queryWrapper);
        if (doctorlogin != null) {
            //设置cookie
            String cookieValue = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_web_admin", cookieValue);
            cookie.setPath("/");
            response.addCookie(cookie);
            //在redis中存储
            redisTemplate.boundValueOps(cookieValue).set(doctorlogin.getPermission(), 240, TimeUnit.MINUTES);
            return JSON.toJSONString(ResponseResult.getSuccessResult(doctorinformationMapper.selectById(doctorlogin.getId()), "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }

    @Override
    public String editDoctorLoginByDoctor(EditDoctorLoginByDoctor editDoctorLogin) {
        //检测旧密码是否正确
        QueryWrapper<Doctorlogin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id", editDoctorLogin.getId())
                        .eq("password", DigestUtils.md5DigestAsHex(editDoctorLogin.getPassword().getBytes(StandardCharsets.UTF_8)));
        if(getOne(queryWrapper)==null){
            //密码错误
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        UpdateWrapper<Doctorlogin> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id", editDoctorLogin.getId())
                        .set("password", DigestUtils.md5DigestAsHex(editDoctorLogin.getNewPassword().getBytes(StandardCharsets.UTF_8)));
        if(update(updateWrapper)){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        }else{
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




