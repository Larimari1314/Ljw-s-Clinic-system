package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "统一修改医生值班信息")
public class EditUniteDutyTimeByAdmin {
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("科室")
    private String department;
    @ApiModelProperty("挂号级别")
    private String registered;
    @ApiModelProperty("班次设定")
    @NotBlank(message = "班次设定不得为空")
    @Pattern(regexp = "^GT[0-9]{3}$", message = "编码格式错误")
    private String scheduleRule;
    @ApiModelProperty("起始年龄")
    private Integer startAge;
    @ApiModelProperty("终止年龄")
    private Integer endAge;
}
