package sale.ljw.clinicsystem.common.sercurity.intercept;

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
                "/loginAdmin/GetAllAds",
                "/loginAdmin/getAdminImgUrl/**",
                "/loginAdmin/login",
                "/loginAdmin/logout",
                "/loginAdmin/upload",
                "/Img/**/**",
                "/department/upload",
                "/drug/upload",
                "/drug/uploadToServer",
                "/LandingAds/upload",
                "/doctorInformation/uploadToServer",
                "/patientInformation/upload",
                "/doctorInformation/upload",
                "/patientInformation/uploadToServer",
                "/doctorInformationByDoctor/upload",
                "/swagger-ui.html/**",
                "/webjars/**",
                "/swagger-resources/**",
                "/loginDoctor/login",
                "/loginPatient/login",
                "/departmentByPatient/payAppointment/**",
                "/departmentByPatient/scanPayStatus/**",
                "/orderByPatient/scanPayStatusOrder/**",
                "/orderByPatient/payingOrder/**",
                "/patientInformationByPatient/upload",
                "/patientInformationByPatient/registerAccountByPatient"
        );
    }
}
