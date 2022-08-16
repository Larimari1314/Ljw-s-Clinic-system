package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import sale.ljw.clinicsystem.backend.pojo.basic.Drug;
import sale.ljw.clinicsystem.backend.service.basic.DrugService;
import sale.ljw.clinicsystem.backend.dao.basic.DrugMapper;
import org.springframework.stereotype.Service;

/**
* @author 86155
* @description 针对表【drug】的数据库操作Service实现
* @createDate 2022-08-07 16:05:39
*/
@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug>
    implements DrugService{

}




