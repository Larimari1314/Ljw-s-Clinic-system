package sale.ljw.clinicsystem.backend.service.order;

import sale.ljw.clinicsystem.backend.form.order.admin.FindOrderFromByAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Orderform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86155
* @description 针对表【OrderForm】的数据库操作Service
* @createDate 2022-08-07 16:11:50
*/
public interface OrderformService extends IService<Orderform> {

    String findAllOrderFrom(FindOrderFromByAdmin orderFrom);

    String findOrderDrugList(String id);

    String getTotalPrice(String id);

    String invoiceService(String id);
}
