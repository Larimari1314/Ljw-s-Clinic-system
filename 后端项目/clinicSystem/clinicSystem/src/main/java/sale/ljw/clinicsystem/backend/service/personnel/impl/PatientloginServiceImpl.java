package sale.ljw.clinicsystem.backend.service.personnel.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sale.ljw.clinicsystem.backend.dao.personnel.PatientinformationMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.PatientloginMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientLoginByPatient;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin;
import sale.ljw.clinicsystem.backend.service.personnel.PatientloginService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 86155
 * @description 针对表【patientLogin】的数据库操作Service实现
 * @createDate 2022-08-07 16:02:47
 */
@Service
public class PatientloginServiceImpl extends ServiceImpl<PatientloginMapper, Patientlogin>
        implements PatientloginService {
    @Autowired
    private PatientloginMapper patientloginMapper;
    @Autowired
    private PatientinformationMapper patientinformationMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(LoginFrom login, HttpServletResponse response) {
        QueryWrapper<Patientlogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(login.getPassword().getBytes(StandardCharsets.UTF_8)))
                .eq("LoginName", login.getLoginCredentials())
                .or().eq("LoginPhone", login.getLoginCredentials())
                .or().eq("LoginEmail", login.getLoginCredentials());
        Patientlogin patientlogin = patientloginMapper.selectOne(queryWrapper);
        if (patientlogin != null) {
            //设置cookie
            String cookieValue = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_web_admin", cookieValue);
            cookie.setPath("/");
            response.addCookie(cookie);
            //在redis中存储
            redisTemplate.boundValueOps(cookieValue).set(patientlogin.getPermission(), 240, TimeUnit.MINUTES);
            return JSON.toJSONString(ResponseResult.getSuccessResult(patientinformationMapper.selectById(patientlogin.getId()), "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }

    @Override
    public String editPatientLoginByPatient(EditPatientLoginByPatient editPatientLogin) {
        //修改
        UpdateWrapper<Patientlogin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", editPatientLogin.getId())
                .eq("password", DigestUtils.md5DigestAsHex(editPatientLogin.getPassword().getBytes(StandardCharsets.UTF_8)))
                .set("password", DigestUtils.md5DigestAsHex(editPatientLogin.getNewPassword().getBytes(StandardCharsets.UTF_8)));
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            //密码错误
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




