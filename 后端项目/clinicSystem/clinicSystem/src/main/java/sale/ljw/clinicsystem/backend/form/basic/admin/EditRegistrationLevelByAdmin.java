package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@ApiModel(value = "编辑挂号级别")
public class EditRegistrationLevelByAdmin {
    @ApiModelProperty(value = "挂号id")
    @NotBlank(message = "挂号id不得为空")
    @Pattern(regexp = "^RE[0-9]{3}$", message = "编码格式错误")
    private String id;
    @ApiModelProperty(value = "挂号金额")
    @NotNull(message = "挂号金额不得为空")
    @Max(value = 500,message = "挂号金额过多")
    @Min(value = 10,message = "挂号金额过少")
    private Integer price;
}
