package sale.ljw.clinicsystem.backend.api.order;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.order.admin.FindOrderFromByAdmin;
import sale.ljw.clinicsystem.backend.service.order.OrderformService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderServlet {
    @Autowired
    private OrderformService orderformService;

    @ApiOperation(value = "获取全部订单")
    @PostMapping("/findAllOrderFrom")
    public String findAllOrderFrom(@RequestBody @Valid FindOrderFromByAdmin orderFrom) {
        return orderformService.findAllOrderFrom(orderFrom);
    }

    @ApiOperation(value = "获取订单药品")
    @GetMapping("/findOrderDrugList/{id}")
    public String findOrderDrugList(@PathVariable String id) {
        return orderformService.findOrderDrugList(id);
    }

    @ApiOperation(value = "获取总价")
    @GetMapping("/getTotalPrice/{id}")
    public String getTotalPrice(@PathVariable String id) {
        return orderformService.getTotalPrice(id);
    }

    @ApiOperation(value = "生成发票")
    @GetMapping("/invoiceService/{id}")
    public String invoiceService(@PathVariable String id){
        return orderformService.invoiceService(id);
    }
}
