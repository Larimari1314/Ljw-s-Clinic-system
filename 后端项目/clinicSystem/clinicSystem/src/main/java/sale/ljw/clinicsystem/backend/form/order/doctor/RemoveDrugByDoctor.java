package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "删除药品信息")
public class RemoveDrugByDoctor {
    @ApiModelProperty(value = "订单id")
    @NotBlank(message = "订单id不得为空")
    private String orderId;
    @ApiModelProperty(value = "药品id")
    @NotBlank(message = "药品id不得为空")
    private String drugId;
    @ApiModelProperty(value = "药品退回数量")
    @NotNull(message = "药品退回数量不得为空")
    @Min(1)
    @Max(200)
    private Integer number;
}
