package sale.ljw.clinicsystem.backend.dao.order;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.pojo.order.Orderdrug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 86155
* @description 针对表【orderDrug】的数据库操作Mapper
* @createDate 2022-08-07 16:11:27
* @Entity sale.ljw.clinicsystem.backend.pojo.order.Orderdrug
*/
@Mapper
@Repository
public interface OrderdrugMapper extends BaseMapper<Orderdrug> {
    ArrayList<Map<String, Object>> findByDrugIds(List<String> ids);
}




