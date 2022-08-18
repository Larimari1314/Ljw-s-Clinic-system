package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "添加药品")
public class AddDrugByAdmin {
    @ApiModelProperty(value = "药品名称")
    @NotBlank(message = "药品名称不得为空")
    private String drugname;
    @ApiModelProperty(value = "药品数量")
    @NotNull
    private Integer number;
    @ApiModelProperty(value = "药品价格")
    @NotNull
    private Double price;
    @ApiModelProperty(value = "药品规格")
    @NotBlank(message = "药品规格不得为空")
    @Pattern(regexp = "^GD[0-9]{3}$", message = "编码格式错误")
    private String specification;
    @ApiModelProperty(value = "药品封面")
    private String cover;
}
