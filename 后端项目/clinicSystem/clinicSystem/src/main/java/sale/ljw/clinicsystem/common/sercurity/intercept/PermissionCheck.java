package sale.ljw.clinicsystem.common.sercurity.intercept;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sale.ljw.clinicsystem.common.sercurity.utils.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionCheck implements HandlerInterceptor {

    private static final String OPTIONS = "OPTIONS";
    @Autowired
    JudgmentOfAuthority judgmentOfAuthority;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //获取token值
        String token = request.getHeader("token");
        //检测token值是否还在有效期
        if(!JwtUtils.verify(token)){
            //401登录失效
            loginFailureResponse(response);
            return false;
        }
        //token有效，检测用户权限是否有效
        return judgmentOfAuthority.findPermission(token, request,response);
    }

    private void loginFailureResponse(HttpServletResponse response) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
