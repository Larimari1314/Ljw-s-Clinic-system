package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "编辑常规项")
@Data
public class EditGeneralByAdmin {
    @ApiModelProperty(value = "常规项编码")
    @NotBlank(message = "常规项编码不得为空")
    @Pattern(regexp = "^G[0-9a-zA-Z]{4}$", message = "编码格式错误")
    private String coding;

    @ApiModelProperty(value = "常规项值")
    @NotBlank(message = "常规项值不得为空")
    private String value;
}
