package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "根据药品名称查找药品")
public class OrderFindDrugListByDoctor {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "药品名称")
    private String drugName;
}
