package sale.ljw.clinicsystem.backend.form.personnel.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
@Data
@ApiModel(value = "添加患者信息")
public class AddPatientInformationByAdmin {
    @ApiModelProperty(value = "患者姓名")
    @NotBlank(message = "用户名不得为空")
    private String name;
    @ApiModelProperty(value = "生日")
    @NotNull(message = "出生日期不得为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @ApiModelProperty(value = "性别")
    @Pattern(regexp = "^GS[0-9]{3}$", message = "性别编码格式错误")
    @Length(min = 5, max = 5, message = "性别编码长度必须为5位")
    @NotBlank(message = "性别id不得为空")
    private String sex;
    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    @Length(min = 18, max = 18)
    private String identityNumber;
    @ApiModelProperty(value = "家庭住址")
    @NotBlank(message = "家庭住址不得为空")
    private String address;
}
