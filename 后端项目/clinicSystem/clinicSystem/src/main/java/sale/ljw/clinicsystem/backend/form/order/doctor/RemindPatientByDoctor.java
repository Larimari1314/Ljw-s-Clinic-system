package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "提醒患者")
public class RemindPatientByDoctor {
    @ApiModelProperty(value = "订单id")
    @NotBlank(message = "订单id")
    private String id;
    @ApiModelProperty(value = "提醒次数")
    @NotNull
    private Integer numberreminders;
}
