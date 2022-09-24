package sale.ljw.clinicsystem.backend.form.basic.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找历史订单")
public class FindAllHistoryMedicalTechnology {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;
}
