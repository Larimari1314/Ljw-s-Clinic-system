package sale.ljw.clinicsystem.backend.form.personnel.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改医生登录信息")
public class EditDoctorLoginByDoctor {
    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不得为空")
    private String id;
    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不得为空")
    private String password;
    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不得为空")
    private String newPassword;
}
