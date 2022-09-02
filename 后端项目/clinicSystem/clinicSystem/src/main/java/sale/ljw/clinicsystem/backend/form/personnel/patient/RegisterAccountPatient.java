package sale.ljw.clinicsystem.backend.form.personnel.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ApiModel(value = "注册患者")
public class RegisterAccountPatient {
    @ApiModelProperty("登录名")
    @NotBlank(message = "登录名不得为空")
    private String loginname;
    @ApiModelProperty(value = "登录邮件")
    @NotBlank(message = "登录邮件")
    @Pattern(regexp = "/^([-_A-Za-z0-9.]+)@([_A-Za-z0-9]+\\.)+[A-Za-z0-9]{2,3}$/", message = "编码格式错误")
    private String loginemail;
    @ApiModelProperty(value = "登录电话")
    @NotBlank(message = "登陆电话不得为空")
    @Pattern(regexp = "/^[1][3-9][0-9]{9}$/", message = "编码格式错误")
    private String loginphone;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不得为空")
    private String password;
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不得为空")
    private String name;
    @ApiModelProperty(value = "生日")
    @NotBlank(message = "生日不得为空")
    private Date birthday;
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不得为空")
    @Pattern(regexp = "^GS[0-9]{3}$", message = "编码格式错误")
    private String sex;
    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不得为空")
    @Pattern(regexp = "/^([-_A-Za-z0-9.]+)@([_A-Za-z0-9]+\\.)+[A-Za-z0-9]{2,3}$/", message = "编码格式错误")
    private String identitynumber;
    @ApiModelProperty(value = "家庭住址")
    @NotBlank(message = "家庭住址不得为空")
    private String address;
}
