package sale.ljw.clinic_administrator_background_system.service.login;

import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminLoginService {
    public abstract String addAmin(AdminLogin adminLogin);

    public abstract String getImgUrl(String adminUserName);

    public abstract String adminLogin(AdminLogin adminLogin, HttpServletResponse resp, HttpServletRequest req);

    public abstract String editAdminLogin(AdminLogin adminLogin);
}
