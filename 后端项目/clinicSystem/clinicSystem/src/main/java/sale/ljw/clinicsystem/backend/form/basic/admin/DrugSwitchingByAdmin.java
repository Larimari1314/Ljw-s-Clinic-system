package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "药品开关设置")
@Data
public class DrugSwitchingByAdmin {
    @ApiModelProperty(value = "药品id")
    @NotBlank
    private String id;
    @ApiModelProperty(value = "药品当前状态")
    @NotNull
    private Integer enable;
}
