package sale.ljw.clinic_administrator_background_system.service.login.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AdminInformationDao;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AdminPermissionDao;
import sale.ljw.clinic_administrator_background_system.dao.login.AdminLoginDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminLogin;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.login.AdminLoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AdminLoginDao adminLoginDao;
    @Autowired
    private AdminInformationDao adminInformationDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminPermissionDao adminPermissionDao;

    @Override
    public String addAmin(AdminLogin adminLogin) {
        try {
            if (adminLoginDao.findByUsername(adminLogin.getAdminUserName()) == null) {
                String newPassword = DigestUtils.md5DigestAsHex(adminLogin.getPassword().getBytes(StandardCharsets.UTF_8));
                adminLogin.setPassword(newPassword);
                adminLogin.setId(UUID.randomUUID().toString());
                adminLoginDao.addAmin(adminLogin);
                WebMessage webMessage = new WebMessage(Code.success_code, "添加成功", "");
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                WebMessage webMessage = new WebMessage("用户名已存在", "");
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @Override
    public String getImgUrl(String adminUserName) {
        try {
            AdminLogin byUsername = adminLoginDao.findByUsername(adminUserName);
            if (byUsername != null) {
                WebMessage webMessage = new WebMessage(Code.success_code, "欢迎用户" + adminUserName + "，请填写密码登录", adminInformationDao.findImgUrl(byUsername.getId()));
                return JSON.toJSONString(webMessage);
            } else {
                WebMessage webMessage = new WebMessage("未查找到用户名请重新填写", "");
                return JSON.toJSONString(webMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebMessage webMessage = new WebMessage("出现错误，请联系管理员或稍后重试", "");
            return JSON.toJSONString(webMessage);
        }
    }

    @Override
    public String adminLogin(AdminLogin adminLogin, HttpServletResponse resp, HttpServletRequest req) {
        String newPassword = DigestUtils.md5DigestAsHex(adminLogin.getPassword().getBytes(StandardCharsets.UTF_8));
        adminLogin.setPassword(newPassword);
        AdminLogin admin = adminLoginDao.adminLogin(adminLogin);
        if (admin != null) {
            //登陆成功，首先在cookie中存储用户id，并将创建一个session会话，规定会话结束时间
            String cookieValue = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_web_admin", cookieValue);
            cookie.setPath("/");
            resp.addCookie(cookie);
            //设置session
        /*    HttpSession session = req.getSession();
            String redisKey = UUID.randomUUID().toString();
            session.setAttribute(admin.getId(), redisKey);
            session.setMaxInactiveInterval(10 * 60);*/
            //将数据存储到redis缓存库中，设置事件为十分钟,查找用户权限存储redis中
            redisTemplate.boundValueOps(cookieValue).set(adminPermissionDao.getAccess(admin.getId()).getPermission(), 60, TimeUnit.MINUTES);
            //此时全部结束返回到界面
            WebMessage webMessage = new WebMessage(Code.success_code, "登陆成功", adminInformationDao.findById(admin.getId()));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            WebMessage webMessage = new WebMessage(Code.known_error_code, "密码错误，请检查后输入", "");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String editAdminLogin(AdminLogin adminLogin) {
        try{
            String newPassword = DigestUtils.md5DigestAsHex(adminLogin.getPassword().getBytes(StandardCharsets.UTF_8));
            adminLogin.setPassword(newPassword);
            Integer integer = adminLoginDao.editAdminLogin(adminLogin);
            WebMessage webMessage=new WebMessage();
            if(integer!=0){
                webMessage.setCode(Code.success_code);
                webMessage.setMessage("修改成功");
                return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
            }else {
                webMessage.setCode(Code.known_error_code);
                webMessage.setMessage("修改失败，请稍后重试");
                return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
            }
        }catch (Exception e){
            WebMessage webMessage=new WebMessage("修改失败，请稍后重试","");
            return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
