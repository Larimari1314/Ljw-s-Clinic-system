package sale.ljw.clinicsystem.backend.service.personnel.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.personnel.AdmininformationMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.AdminloginMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditAdminLogin;
import sale.ljw.clinicsystem.backend.form.basic.admin.LoginFrom;
import sale.ljw.clinicsystem.backend.pojo.personnel.Admininformation;
import sale.ljw.clinicsystem.backend.pojo.personnel.Adminlogin;
import sale.ljw.clinicsystem.backend.service.personnel.AdminloginService;
import sale.ljw.clinicsystem.common.http.ResponseResult;
import sale.ljw.clinicsystem.common.sercurity.utils.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 86155
 * @description 针对表【adminLogin】的数据库操作Service实现
 * @createDate 2022-08-07 16:00:14
 */
@Service
public class AdminloginServiceImpl extends ServiceImpl<AdminloginMapper, Adminlogin>
        implements AdminloginService {
    @Autowired
    private AdmininformationMapper admininformationMapper;
    @Autowired
    private AdminloginMapper adminloginMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getAdminImgUrl(String loginCredentials) {
        QueryWrapper<Adminlogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("LoginName", loginCredentials)
                .or().eq("LoginPhone", loginCredentials)
                .or().eq("LoginEmail", loginCredentials);
        List<Adminlogin> list = list(queryWrapper);
        if (list.size() == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        } else if (list.size() > 1) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C201"));
        }
        Adminlogin adminlogin = list.get(0);
        QueryWrapper<Admininformation> queryWrapperInformation = new QueryWrapper<>();
        queryWrapperInformation.eq("id", adminlogin.getId())
                .select("avatar");
        return JSON.toJSONString(ResponseResult.getSuccessResult(admininformationMapper.selectOne(queryWrapperInformation).getAvatar()));
    }

    @Override
    public String login(LoginFrom login, HttpServletResponse response) {
        QueryWrapper<Adminlogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(login.getPassword().getBytes(StandardCharsets.UTF_8)))
                .eq("LoginName", login.getLoginCredentials())
                .or().eq("LoginPhone", login.getLoginCredentials())
                .or().eq("LoginEmail", login.getLoginCredentials());
        Adminlogin adminlogin = adminloginMapper.selectOne(queryWrapper);
        if (adminlogin != null) {
            /*// 设置cookie
            String cookieValue = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_web_admin", cookieValue);
            cookie.setPath("/");
            response.addCookie(cookie);
            //在redis中存储
            redisTemplate.boundValueOps(cookieValue).set(adminlogin.getPermission(), 60, TimeUnit.MINUTES);
            return JSON.toJSONString(ResponseResult.getSuccessResult(admininformationMapper.selectById(adminlogin.getId()), "C200", null));
            */
            //将用户id和用户权限绑定在token中并放置在请求头中，发送到前端
            String token = JwtUtils.sign("admin", adminlogin.getId());
            //将信息写入请求头中
            response.setHeader("token",token);
            //返回页面信息
            return JSON.toJSONString(ResponseResult.getSuccessResult(admininformationMapper.selectById(adminlogin.getId()), "C200", null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
    }

    @Override
    public String logout(HttpServletResponse response) {
        response.setHeader("permissionToken",null);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
    }

    @Override
    public String aditAdminInformation(EditAdminInformationByAdmin adminInformation, MultipartFile multipartFile) {
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\drug\\" + adminInformation.getId() + "\\" + adminInformation.getId() + ".jpg");
            try {
                multipartFile.transferTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Admininformation newAdminInformation = new Admininformation();
        BeanUtils.copyProperties(adminInformation, newAdminInformation);
        int update = admininformationMapper.updateById(newAdminInformation);
        if (update != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String editAdminLogin(EditAdminLogin adminLogin) {
        UpdateWrapper<Adminlogin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", adminLogin.getId())
                .set("LoginEmail", adminLogin.getLoginEmail())
                .set("LoginName", adminLogin.getLoginName())
                .set("LoginPhone", adminLogin.getLoginPhone())
                .set("password", DigestUtils.md5DigestAsHex(adminLogin.getPassword().getBytes(StandardCharsets.UTF_8)));
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
}




