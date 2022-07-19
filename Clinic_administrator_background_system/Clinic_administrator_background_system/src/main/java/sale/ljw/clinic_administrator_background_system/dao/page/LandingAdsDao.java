package sale.ljw.clinic_administrator_background_system.dao.page;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.page.LandingAds;

import java.util.ArrayList;

@Mapper
@Repository
public interface LandingAdsDao {
    public abstract ArrayList<LandingAds> findAllAds();
}
