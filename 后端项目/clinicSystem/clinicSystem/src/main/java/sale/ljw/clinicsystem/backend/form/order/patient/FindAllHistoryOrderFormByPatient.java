package sale.ljw.clinicsystem.backend.form.order.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询全部历史订单")
public class FindAllHistoryOrderFormByPatient {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "患者id")
    @NotBlank(message = "患者id不得为空")
    private String patientId;
    @ApiModelProperty(value = "医生名称")
    private String doctorName;
}
