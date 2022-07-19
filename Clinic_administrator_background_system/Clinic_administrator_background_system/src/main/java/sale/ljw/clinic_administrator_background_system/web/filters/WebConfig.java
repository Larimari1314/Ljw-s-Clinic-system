package sale.ljw.clinic_administrator_background_system.web.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    public PermissionCheck permissionCheck;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionCheck).addPathPatterns("/**").excludePathPatterns(
                "/login/**",
                "/img/**",
                "/page/**",
                "/patient/uploadToServer",
                "/doctor/uploadToServer",
                "/drug/uploadToServer",
                "/error/**",
                "/adminInformation/uploadToServer");
    }
}
