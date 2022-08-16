package sale.ljw.clinicsystem.backend.form.personnel.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "更新医生信息")
public class EditDoctorInformation implements Serializable {
    @ApiModelProperty(value = "医生id")
    @NotEmpty(message = "id不得为空")
    private String id;
    @ApiModelProperty(value = "挂号id")
    @Pattern(regexp = "^RE[0-9]{3}$", message = "挂号编码格式错误")
    @NotBlank(message = "挂号id不得为空")
    @Length(min = 5, max = 5,message = "挂号id长度必须为五位")
    private String registereId;
    @ApiModelProperty(value = "性别id")
    @NotBlank(message = "性别id不得为空")
    @Pattern(regexp = "^GS[0-9]{3}$", message = "性别编码格式错误")
    @Length(min = 5, max = 5,message = "性别长度必须为五位")
    private String sexCoding;
    @ApiModelProperty(value = "头像id")
    @NotBlank(message = "头像不得为空")
    private String avatar;
    @ApiModelProperty(value = "家庭住址")
    @NotBlank(message = "家庭住址不得为空")
    private String address;
    @ApiModelProperty(value = "出生日期")
    @NotNull(message = "出生日期不得为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdayCoding;
    @ApiModelProperty(value = "科室id")
    @Pattern(regexp = "^DE[0-9]{3}$", message = "科室编码格式错误")
    @NotBlank(message = "科室id不得为空")
    @Length(min = 5, max = 5,message = "科室id长度必须为五位")
    private String did;
    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不得为空")
    private String name;

}
