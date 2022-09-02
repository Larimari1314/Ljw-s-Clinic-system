package sale.ljw.clinicsystem.backend.api.patient.order;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.order.patient.FindAllHistoryOrderFormByPatient;
import sale.ljw.clinicsystem.backend.form.order.patient.FindUnpaidOrderFormByPatient;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;

@CrossOrigin
@RestController
@RequestMapping("/orderByPatient")
public class OrderServletByPatient {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation(value = "查询历史订单")
    @PostMapping("/findAllHistoryOrderForm")
    public String findAllHistoryOrderForm(@RequestBody FindAllHistoryOrderFormByPatient historyOrderForm) {
        return reserveService.findAllHistoryOrderForm(historyOrderForm);
    }

    @ApiOperation(value = "查看订单完成订单")
    @GetMapping("/findOrderCompletion/{orderId}")
    public String findOrderCompletion(@PathVariable String orderId) {
        return reserveService.findOrderCompletion(orderId);
    }

    @ApiOperation(value = "获取未支付订单")
    @PostMapping("/findUnpaidOrderForm")
    public String findUnpaidOrderForm(@RequestBody FindUnpaidOrderFormByPatient unpaidOrderForm) {
        return reserveService.findUnpaidOrderForm(unpaidOrderForm);
    }

    @ApiOperation(value = "获取订单支付获取信息")
    @GetMapping("/getOrderInformation/{pagId}")
    public String getOrderInformation(@PathVariable String pagId) {
        return reserveService.getOrderInformation(pagId);
    }

    @ApiOperation(value = "支付id和订单绑定")
    @GetMapping("/payOrderBounding/{orderId}")
    public String payOrderBounding(@PathVariable String orderId){
        return reserveService.payOrderBoundingByPatient(orderId);
    }

    @ApiOperation(value = "获取订单详情")
    @GetMapping("/getOrderInformationPaying/{orderId}")
    public String getOrderInformationPaying(@PathVariable String orderId){
        return reserveService.getOrderInformationPaying(orderId);
    }
    @ApiOperation(value = "获取支付图片")
    @GetMapping("/getQRCodeOrder/{orderId}")
    public String getQRCodeOrder(@PathVariable String orderId){
        return reserveService.getQRCodeOrder(orderId);
    }

    @ApiOperation(value = "修改支付状态")
    @GetMapping("/scanPayStatusOrder/{orderId}")
    public String scanPayStatusOrder(@PathVariable String orderId){
        return reserveService.scanPayStatusOrder(orderId);
    }

    @ApiOperation(value = "支付订单")
    @GetMapping("/payingOrder/{orderId}")
    public String payingOrder(@PathVariable String orderId){
        return reserveService.payingOrder(orderId);
    }

    @ApiOperation(value = "撤销预约")
    @GetMapping("/refundOperationOrder/{orderId}")
    public String refundOperationOrder(@PathVariable String orderId){
        return reserveService.refundOperationOrder(orderId);
    }
    @ApiOperation(value = "获取医生提醒次数")
    @GetMapping("/getRemindersNumber/{patientId}")
    public String getRemindersNumber(@PathVariable String patientId){
        return reserveService.getRemindersNumber(patientId);
    }
}
