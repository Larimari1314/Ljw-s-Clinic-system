package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找广告")
public class FindAllLandingAds {
    @ApiModelProperty(value = "页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "名称")
    private String name;
}
