package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminPermission;

@Mapper
@Repository
public interface AdminPermissionDao {
    public abstract AdminPermission getAccess(String id);
}
