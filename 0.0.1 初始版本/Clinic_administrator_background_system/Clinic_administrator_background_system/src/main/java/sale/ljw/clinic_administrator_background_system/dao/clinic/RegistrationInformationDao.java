package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.RegistrationInformation;

import java.util.ArrayList;

@Mapper
@Repository
public interface RegistrationInformationDao {
    public abstract ArrayList<RegistrationInformation> findById(String id);

}
