package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改管理员登录信息")
public class EditAdminLogin {
    @ApiModelProperty(value = "登录名")
    @NotBlank(message = "登录名不得为空")
    private String LoginName;
    @ApiModelProperty(value = "id")
    @NotBlank(message = "登录id不得为空")
    private String id;
    @ApiModelProperty(value = "登录电话号")
    @NotBlank(message = "登录电话号不得为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "电话号格式错误")
    private String LoginPhone;
    @ApiModelProperty(value = "登录邮件")
    @NotBlank(message = "登录邮件不得为空")
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮件格式错误")
    private String LoginEmail;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不得为空")
    private String password;
}
