package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ApiModel(value = "修改管理员信息")
public class EditAdminInformationByAdmin {
    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不得为空")
    private String id;
    @ApiModelProperty(value = "名字")
    @NotBlank(message = "名称不得为空")
    private String name;
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不得为空")
    @Pattern(regexp = "^GS[0-9]{3}$", message = "编码格式错误")
    private String sex;
    @ApiModelProperty(value = "年龄")
    @NotNull(message = "年龄不得为空")
    private Integer age;
    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    @Length(min = 18, max = 18)
    private String identitynumber;
    @ApiModelProperty(value = "地址不得为空")
    @NotBlank(message = "地址不得为空")
    private String address;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "生日")
    private Date birthday;
}
