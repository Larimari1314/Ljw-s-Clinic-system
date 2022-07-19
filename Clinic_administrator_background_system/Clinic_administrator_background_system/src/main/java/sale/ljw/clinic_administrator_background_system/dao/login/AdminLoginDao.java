package sale.ljw.clinic_administrator_background_system.dao.login;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminLogin;
@Mapper
@Repository
public interface AdminLoginDao {
    public abstract Integer addAmin(AdminLogin adminLogin);

    public abstract AdminLogin findByUsername(String adminUserName);

    public abstract AdminLogin adminLogin(AdminLogin adminLogin);

    public abstract Integer editAdminLogin(AdminLogin adminLogin);

}
