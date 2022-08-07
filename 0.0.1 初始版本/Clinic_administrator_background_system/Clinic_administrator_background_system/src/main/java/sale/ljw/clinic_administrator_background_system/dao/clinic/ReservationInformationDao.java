package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.ReservationInformation;

import java.util.ArrayList;
@Mapper
@Repository
public interface ReservationInformationDao {
    /**
     * 根据医生id查询预约情况
     * @param did
     * @return
     */
    public abstract ArrayList<ReservationInformation> findByDid(String did);

    /**
     * 查找全部预约信息
     * @return
     */
    public abstract ArrayList<ReservationInformation> findAll();

    /**
     * 删除预约状态
     * @param id
     * @return
     */
    public abstract Integer deleteReservation(String id);

    public abstract ReservationInformation findById(String id);

    public abstract ArrayList<ReservationInformation> findByName(ArrayList<String> id);
}
