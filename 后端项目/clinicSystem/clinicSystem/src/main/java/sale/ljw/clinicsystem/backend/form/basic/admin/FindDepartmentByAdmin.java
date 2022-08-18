package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找科室")
public class FindDepartmentByAdmin {
    @ApiModelProperty(value = "科室名称")
    private String name;
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
}
