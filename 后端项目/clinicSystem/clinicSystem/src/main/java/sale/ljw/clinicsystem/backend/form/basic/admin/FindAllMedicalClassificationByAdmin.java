package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询医疗技术")
public class FindAllMedicalClassificationByAdmin {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "医疗技术名称")
    private String medicalName;
}
