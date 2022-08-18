package sale.ljw.clinicsystem.backend.form.basic.admin;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改药品信息")
public class EditDrugByAdmin {
    @ApiModelProperty(value = "药品id")
    @NotBlank(message = "药品id不得为空")
    private String id;
    @ApiModelProperty(value = "药品名称")
    @NotBlank(message = "药品名称不得为空")
    private String drugName;
    @ApiModelProperty(value = "药品规格")
    @NotBlank(message = "药品规格不得为空")
    @Pattern(regexp = "^GD[0-9]{3}$", message = "编码格式错误")
    private String specification;
    @ApiModelProperty(value = "药品数量")
    @NotNull(message = "药品数量不得为空")
    private Integer number;
    @ApiModelProperty(value = "药品单价")
    @NotNull(message = "药品单价不得为空")
    private Double price;
    @ApiModelProperty(value = "药品封面")
    @NotBlank(message = "药品封面不得为空")
    private String cover;
}
