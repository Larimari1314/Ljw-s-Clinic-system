package sale.ljw.clinicsystem.common.sercurity.intercept;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/*

@Component
public class JudgmentOfAuthority {
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private PermissionService permissionService;

    public Boolean findPermission(String redisKey, HttpServletRequest request, HttpServletResponse response) {
        try {
            String servletPath = request.getServletPath();
            if("/Img".equals(servletPath.substring(0, 4))){
                return true;
            }
            if (redisTemplate.boundValueOps(redisKey).get() != null) {
                int per = (int) redisTemplate.boundValueOps(redisKey).get();
                QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("p_id", per).eq("p_url", servletPath);
                Map<String, Object> map =permissionService.getMap(queryWrapper);
                if (map!=null) {
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
*/
