package sale.ljw.clinicsystem.backend.api.basic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllLandingAds;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.service.basic.LandingadsService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/LandingAds")
public class LandingAdsServlet {
    @Autowired
    private LandingadsService landingadsService;
    private MultipartFile multipartFile;

    @ApiOperation(value = "获取全部广告")
    @PostMapping("/findAllLandingAds")
    public String findAllLandingAds(@RequestBody @Valid FindAllLandingAds landingAds) {
        return landingadsService.findAllLandingAds(landingAds);
    }

    @ApiOperation(value = "广告封面上传暴漏端口")
    @PostMapping("/upload")
    public void upload(@RequestBody MultipartFile file) {
        multipartFile = file;
    }

    @ApiOperation(value = "编辑广告")
    @PostMapping("/editLandingAds")
    public String editLandingAds(@RequestBody @Valid EditLandingAdsByAdmin landingAds) {
        String result = landingadsService.editLandingAds(landingAds, multipartFile);
        multipartFile = null;
        return result;
    }

    @ApiOperation(value = "删除广告")
    @PostMapping("/deleteLandingAds")
    public String deleteLandingAds(@RequestBody @Valid DeleteIdsBYAdmin deleteById) {
        return landingadsService.deleteLandingAds(deleteById);
    }

    @ApiOperation(value = "添加广告")
    @PostMapping("/addLandingAds")
    public String addLandingAds(@RequestBody @Valid AddLandingAdsByAdmin landingAds){
        return landingadsService.addLandingAds(landingAds,multipartFile);
    }
}
