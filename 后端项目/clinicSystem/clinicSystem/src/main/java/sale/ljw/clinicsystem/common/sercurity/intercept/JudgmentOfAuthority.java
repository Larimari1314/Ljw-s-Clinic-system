package sale.ljw.clinicsystem.common.sercurity.intercept;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sale.ljw.clinicsystem.backend.dao.basic.PermissionMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.AdminloginMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorloginMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.PatientloginMapper;
import sale.ljw.clinicsystem.backend.pojo.basic.Permission;
import sale.ljw.clinicsystem.backend.pojo.personnel.Adminlogin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin;
import sale.ljw.clinicsystem.backend.service.basic.PermissionService;
import sale.ljw.clinicsystem.backend.service.personnel.AdminloginService;
import sale.ljw.clinicsystem.common.sercurity.utils.JwtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JudgmentOfAuthority {
    @Autowired
    private AdminloginMapper adminloginMapper;
    @Autowired
    private PatientloginMapper patientloginMapper;
    @Autowired
    private DoctorloginMapper doctorloginMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public Boolean findPermission(String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String servletPath = request.getServletPath();
            if((servletPath.length()-servletPath.replace("/", "").length())>2){
                servletPath=servletPath.substring(0,servletPath.lastIndexOf('/'));
            }
            if("/Img".equals(servletPath.substring(0, 4))){
                return true;
            }
            Integer permission=null;
            //判断权限
            String permissionName = JwtUtils.parsePermission(token);
            //查找id
            String id = JwtUtils.parseJWT(token);
            if("admin".equals(permissionName)){
                //管理员状态,查找管理员权限id封装到Integer permission
                QueryWrapper<Adminlogin> queryWrapper_permission=new QueryWrapper<>();
                queryWrapper_permission.eq("id",id).select("permission");
                permission=adminloginMapper.selectOne(queryWrapper_permission).getPermission();
            }
            if("doctor".equals(permissionName)){
                //医生状态,查找医生权限id封装到Integer permission
                QueryWrapper<Doctorlogin> queryWrapper_permission=new QueryWrapper<>();
                queryWrapper_permission.eq("id",id).select("permission");
                permission=doctorloginMapper.selectOne(queryWrapper_permission).getPermission();
            }
            if("patient".equals(permissionName)){
                //用户状态,查找用户权限id封装到Integer permission
                QueryWrapper<Patientlogin> queryWrapper_permission=new QueryWrapper<>();
                queryWrapper_permission.eq("id",id).select("permission");
                permission=patientloginMapper.selectOne(queryWrapper_permission).getPermission();
            }
            QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("permission", permission).eq("url", servletPath);
            Long result = permissionMapper.selectCount(queryWrapper);
            if (result!=0) {
                return true;
            } else {
                handleFalseResponse(response);
                return false;
            }
        } catch (Exception e) {
            handleFalseResponse(response);
            e.printStackTrace();
            return false;
        }
    }
    private void handleFalseResponse(HttpServletResponse response) throws Exception {
        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
