package sale.ljw.clinicsystem.common.sercurity.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*

@Component
public class PermissionCheck implements HandlerInterceptor {

    private static final String OPTIONS = "OPTIONS";
    @Autowired
    JudgmentOfAuthority judgmentOfAuthority;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        String redisValue = null;
        boolean sboolean = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //cookie中华由数值则检测
                if ("_neusoft_admin".equals(cookie.getName())) {
                    redisValue = cookie.getValue();
                    sboolean = true;
                }
            }
            if (sboolean) {
//                System.out.println(request.getServletPath() + "权限" + judgmentOfAuthority.findPermission(redisValue, request, response));
                if (judgmentOfAuthority.findPermission(redisValue, request, response)) {
                    return true;
                } else {
                    handleFalseResponse(response);
                    return false;
                }
            } else {
                handleFalseResponse(response);
                return false;
            }
        } else {
            handleFalseResponse(response);
            return false;
        }
    }

    private void handleFalseResponse(HttpServletResponse response) throws Exception {
        response.setStatus(509);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
*/
