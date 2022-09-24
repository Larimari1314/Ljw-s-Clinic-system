package sale.ljw.clinicsystem.backend.form.basic.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找医生列表")
public class FindDoctorListByMedical {
    @ApiModelProperty(value = "科室id")
    @NotBlank(message = "科室id不得为空")
    private String did;

    @ApiModelProperty(value = "常规项目")
    @NotNull(message ="常规项id不得为空")
    private Integer medicalId;

}
