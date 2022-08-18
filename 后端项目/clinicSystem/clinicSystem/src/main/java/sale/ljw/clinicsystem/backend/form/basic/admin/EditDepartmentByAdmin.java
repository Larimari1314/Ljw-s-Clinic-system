package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "编辑科室")
public class EditDepartmentByAdmin {
    @ApiModelProperty(value = "科室id")
    @NotBlank(message = "科室id不得为空")
    @Pattern(regexp = "^DE[0-9]{3}$", message = "编码格式错误")
    private String id;
    @ApiModelProperty("科室名称")
    @NotBlank(message = "科室名称不得为空")
    private String name;
    @ApiModelProperty("科室封面")
    @NotBlank(message = "科室封面不得为空")
    private String cover;
    @ApiModelProperty("科室介绍")
    @NotBlank(message = "科室介绍不得为空")
    private String introduce;
}
