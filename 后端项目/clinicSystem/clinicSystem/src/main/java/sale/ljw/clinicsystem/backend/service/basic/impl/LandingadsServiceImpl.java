package sale.ljw.clinicsystem.backend.service.basic.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.basic.LandingadsMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditLandingAdsByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindAllLandingAds;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Landingads;
import sale.ljw.clinicsystem.backend.service.basic.LandingadsService;
import sale.ljw.clinicsystem.common.http.ResponseResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 86155
 * @description 针对表【landingAds】的数据库操作Service实现
 * @createDate 2022-08-07 16:06:45
 */
@Service
public class LandingadsServiceImpl extends ServiceImpl<LandingadsMapper, Landingads>
        implements LandingadsService {

    @Override
    public String findAllLandingAds(FindAllLandingAds landingAds) {
        PageHelper.startPage(landingAds.getPage(), 10);
        QueryWrapper<Landingads> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("ads", landingAds.getName());
        List<Landingads> list = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(list);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo));
    }

    @Override
    public String editLandingAds(EditLandingAdsByAdmin landingAds, MultipartFile multipartFile) {
        if (multipartFile != null) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\login\\" + landingAds.getId());
            try {
                multipartFile.transferTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        UpdateWrapper<Landingads> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", landingAds.getId()).set("ads", landingAds.getAds());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String deleteLandingAds(DeleteIdsBYAdmin deleteById) {
        //删除广告
        boolean result = removeBatchByIds(deleteById.getIds());
        boolean resultImg = true;
        for (String id : deleteById.getIds()) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\login\\" + id+".jpg");
            boolean delete = newFile.delete();
            if (!delete) {
                resultImg = false;
            }
        }
        if (resultImg == result) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String addLandingAds(AddLandingAdsByAdmin landingAds,MultipartFile multipartFile) {
        if(multipartFile==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        landingAds.setId(RandomUtil.randomInt(0,1000000000));
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\login\\" + landingAds.getId()+".jpg");
        try {
            multipartFile.transferTo(newFile);
            landingAds.setImgurl("http://localhost:8000/clinic//Img//login//"+landingAds.getId()+".jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Landingads landingads=new Landingads();
        BeanUtils.copyProperties(landingAds,landingads);
        boolean save = save(landingads);
        if (save) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String getAllAds() {
        return JSON.toJSONString(list());
    }
}




