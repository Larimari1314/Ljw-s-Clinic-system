package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找药品信息")
public class FindDrugByAdmin {
    @ApiModelProperty(value = "页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    @ApiModelProperty(value = "药品规格")
    private String specification;
}
