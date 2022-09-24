package sale.ljw.clinicsystem.backend.form.basic.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "订单id绑定的订单")
public class FindBindOrderIdMedicalListByDoctor {
    @ApiModelProperty(value = "订单id")
    @NotBlank(message = "订单id不得为空")
    private String orderId;

    @ApiModelProperty(value = "患者id")
    @NotBlank(message = "患者id不得为空")
    private String patientId;
}
