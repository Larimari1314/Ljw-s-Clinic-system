package sale.ljw.clinicsystem.backend.form.personnel.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "添加医生基本信息")
public class AddDoctorInformationByAdmin implements Serializable {
    @ApiModelProperty(value = "医生姓名")
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
    @ApiModelProperty(value = "科室id")
    @Pattern(regexp = "^DE[0-9]{3}$", message = "科室编码格式错误")
    @Length(min = 5, max = 5)
    @NotBlank(message = "科室编码不得为空")
    private String did;
    @ApiModelProperty(value = "薪资")
    @NotNull(message = "薪资不得为空")
    private Double salary;
    @ApiModelProperty(value = "挂号级别id")
    @Length(min = 5, max = 5, message = "挂号id长度必须为五位")
    @Pattern(regexp = "^RE[0-9]{3}$", message = "挂号编码格式错误")
    @NotBlank(message = "挂号id不得为空")
    private String registereId;
    @ApiModelProperty(value = "家庭住址")
    @NotBlank(message = "家庭住址不得为空")
    private String address;
    @ApiModelProperty(value = "值班时间")
    @NotBlank(message = "值班时间不得为空")
    @Pattern(regexp = "^GT[0-9]{3}$", message = "值班编码格式错误" )
    private String duty;


}
