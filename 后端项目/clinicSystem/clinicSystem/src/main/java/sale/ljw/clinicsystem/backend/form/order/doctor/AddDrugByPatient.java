package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "患者配药")
public class AddDrugByPatient {
    @ApiModelProperty("订单id")
    @NotBlank(message = "订单id不得为空")
    private String orderId;
    @ApiModelProperty("药品id")
    @NotBlank(message = "药品id不得为空")
    private String drugId;
    @ApiModelProperty("药品数量")
    @NotNull(message = "药品数量不得为空")
    @Min(1)
    @Max(200)
    private Integer number;

}
