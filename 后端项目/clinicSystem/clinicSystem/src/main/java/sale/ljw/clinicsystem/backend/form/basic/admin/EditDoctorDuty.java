package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改值班时间")
public class EditDoctorDuty {
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String id;
    @ApiModelProperty(value = "医生值班")
    @NotBlank(message = "值班id不得为空")
    @Pattern(regexp = "^GT[0-9]{3}$", message = "编码格式错误")
    private String scheduledCoding;
}
