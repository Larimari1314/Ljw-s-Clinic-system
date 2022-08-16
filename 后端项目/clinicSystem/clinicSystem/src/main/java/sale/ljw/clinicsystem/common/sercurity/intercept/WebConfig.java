package sale.ljw.clinicsystem.common.sercurity.intercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
/*

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    public PermissionCheck permissionCheck;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionCheck).addPathPatterns("/**").excludePathPatterns(
                "/login/getAllAds",
                "/Img/login/**",
                "/Img/user/default.jpg",
                "/login/getAdminImgUrl",
                "/login/adminRequestLogin",
                "/Img/admin/**",
                "/department/avatar",
                "/nondrug/avatar",
                "/nondrug/uploadToServer",
                "/login/avatar",
                "/user/avatar",
                "/user/uploadToServer",
                "/pay/pay",
                "/pay/scanStatus",
                "/swagger-ui.html/**",
                "/webjars/**",
                "/swagger-resources/**"
        );
    }
}
*/
