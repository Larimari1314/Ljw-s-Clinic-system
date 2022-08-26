package sale.ljw.clinicsystem.backend.form.basic.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找发药界面患者")
public class FindDispensingInterfaceByDoctor {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorId;
    @ApiModelProperty(value = "患者名称")
    private String patientName;
    @ApiModelProperty(value = "订单id")
    private String orderId;
}
