package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "编辑广告")
public class EditLandingAdsByAdmin {
    @ApiModelProperty(value = "广告id")
    @NotBlank(message = "广告id不得为空")
    private String id;
    @ApiModelProperty("广告图片地址")
    private String imgurl;
    @ApiModelProperty("广告链接地址")
    @NotBlank(message = "广告指向链接不得为空")
    @Pattern(regexp = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$", message = "广告链接格式错误")
    private String ads;
}
