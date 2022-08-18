package sale.ljw.clinicsystem.backend.service.basic;

import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllLandingAds;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Landingads;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【landingAds】的数据库操作Service
* @createDate 2022-08-07 16:06:45
*/
public interface LandingadsService extends IService<Landingads> {

    String findAllLandingAds(FindAllLandingAds landingAds);

    String editLandingAds(EditLandingAdsByAdmin landingAds, MultipartFile multipartFile);

    String deleteLandingAds(DeleteIdsBYAdmin deleteById);

    String addLandingAds(AddLandingAdsByAdmin landingAds,MultipartFile multipartFile);

    String getAllAds();
}
