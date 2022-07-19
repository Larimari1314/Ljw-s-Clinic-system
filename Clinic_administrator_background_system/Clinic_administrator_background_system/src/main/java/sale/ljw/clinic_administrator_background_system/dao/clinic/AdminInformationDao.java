package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminInformation;

@Mapper
@Repository
public interface AdminInformationDao {
    public abstract String findImgUrl(String id);
    public abstract AdminInformation findById(String id);
    public abstract Integer editAdmin(AdminInformation adminInformation);
}
