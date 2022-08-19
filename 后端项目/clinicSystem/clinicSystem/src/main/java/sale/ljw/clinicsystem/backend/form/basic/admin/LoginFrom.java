package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "登录表单")
public class LoginFrom {
    @ApiModelProperty(value = "登录凭证")
    @NotBlank(message = "登录名不得为空")
    private String loginCredentials;
    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "登陆密码不得为空")
    private String password;
}
