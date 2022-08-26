package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "查看订单")
public class CheckOrderByDoctor {
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorId;

    @ApiModelProperty(value = "预约id")
    @NotBlank(message = "预约id不得为空")
    private String reserveId;
}
