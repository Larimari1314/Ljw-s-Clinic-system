package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "获取全部常规项")
public class FindAllGeneralByAdmin {
    @ApiModelProperty(value = "当前页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "常规项名称")
    private String value;
}
