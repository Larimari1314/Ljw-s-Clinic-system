package sale.ljw.clinicsystem.backend.form.order.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找全部订单")
public class FindOrderFromByAdmin {
    @ApiModelProperty(value = "当前页数")
    @NotNull
    private Integer page;
    @ApiModelProperty("订单id")
    private String orderId;
    @ApiModelProperty(value = "患者姓名")
    private String patientName;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty(value = "科室id")
    private String did;
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
}
