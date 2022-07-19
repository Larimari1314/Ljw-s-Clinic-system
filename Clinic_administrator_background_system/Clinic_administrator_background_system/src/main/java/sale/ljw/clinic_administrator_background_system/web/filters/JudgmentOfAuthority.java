package sale.ljw.clinic_administrator_background_system.web.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AccessControlDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AccessControl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JudgmentOfAuthority {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AccessControlDao accessControlDao;

    public Boolean findPermission(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取请求地址
            String servletPath = request.getServletPath();
            if("/img".equals(servletPath.substring(0, 4))){
                return true;
            }
            //根据redisKey获取value值
            String s1 = StringUtils.substringBefore(servletPath, "/");
            if (redisTemplate.boundValueOps(redisKey).get() != null) {
                //获取到当前权限
                int per = (int) redisTemplate.boundValueOps(redisKey).get();
                //获取到per之后检查当前访问的地址是否能通过
                AccessControl accessControl=new AccessControl(per,servletPath,false);
                AccessControl permissions = accessControlDao.findPermissions(accessControl);
                if (permissions.isAccess()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
