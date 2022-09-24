package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询医技预约订单表中信息")
public class FindMedicalTechnologyOrderByAdmin {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "订单id")
    private String orderId;
}
