package sale.ljw.clinicsystem.backend.form.basic.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找医技表单")
public class FindMedicalTechnologyListByPatient {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "常规项值")
    private String name;
    @ApiModelProperty(value = "医技项目编码")
    private String medicalId;
}
