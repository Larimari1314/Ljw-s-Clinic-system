package sale.ljw.clinic_administrator_background_system.dao.clinic;

import com.rabbitmq.client.AMQP;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AccessControl;
@Mapper
@Repository
public interface AccessControlDao {
    public abstract AccessControl findPermissions(AccessControl accessControl);
}
