package sale.ljw.clinicsystem.backend.form.basic.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找订单")
public class FindReserveByDoctor {
    @ApiModelProperty(value = "患者姓名")
    private String patientName;
    @NotNull
    @ApiModelProperty(value = "当前页数")
    private Integer page;
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorId;
    @ApiModelProperty(value = "患者订单")
    private String orderId;
}
