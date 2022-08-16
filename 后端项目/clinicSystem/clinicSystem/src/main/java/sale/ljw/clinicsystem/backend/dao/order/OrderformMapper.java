package sale.ljw.clinicsystem.backend.dao.order;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.order.admin.FindOrderFromByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Orderform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【OrderForm】的数据库操作Mapper
* @createDate 2022-08-07 16:11:50
* @Entity sale.ljw.clinicsystem.backend.pojo.order.Orderform
*/
@Mapper
@Repository
public interface OrderformMapper extends BaseMapper<Orderform> {
    public ArrayList<Map<String,Object>> findAllOrderFrom(FindOrderFromByAdmin orderFrom);
    public Map<String,Object> findOrderListById(String id);
    public ArrayList<Map<String,Object>> findOrderFrom(String id);
    public Double getOrderTotalPrice(String id);
}




