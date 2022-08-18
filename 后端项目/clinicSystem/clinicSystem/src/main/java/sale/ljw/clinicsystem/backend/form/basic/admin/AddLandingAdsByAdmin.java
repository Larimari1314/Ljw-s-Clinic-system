package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "添加广告")
public class AddLandingAdsByAdmin {
    @ApiModelProperty(value = "广告id")
    private Integer id;
    @ApiModelProperty(value = "图片地址")
    private String imgurl;
    @ApiModelProperty(value = "广告链接")
    @NotBlank(message = "广告链接不得为空")
    @Pattern(regexp = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$", message = "广告链接格式错误")
    private String ads;
}
