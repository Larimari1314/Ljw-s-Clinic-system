package sale.ljw.clinicsystem.backend.form.basic.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "结束就诊")
public class EndOfVisitByDoctor {
    @ApiModelProperty(value = "订单id")
    @NotBlank(message = "订单id不得为空")
    private String id;
    @ApiModelProperty(value = "检查部位")
    @NotBlank(message = "检查部位不得为空")
    private String checkParts;
    @ApiModelProperty(value = "报告")
    @NotBlank(message = "报告不得为空")
    private String inspectionReport;
}
