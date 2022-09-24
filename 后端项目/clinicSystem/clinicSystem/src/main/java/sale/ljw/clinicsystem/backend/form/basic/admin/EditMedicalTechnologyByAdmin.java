package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@ApiModel(value = "编辑信息")
public class EditMedicalTechnologyByAdmin {
    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不得为空")
    private String id;
    @ApiModelProperty(value = "医疗名称")
    @NotBlank(message = "医疗技术名称")
    private String medicalName;
    @ApiModelProperty(value = "医疗分类id")
    @NotBlank(message = "医疗分类id不得为空")
    private String medicalId;
    @ApiModelProperty(value = "医疗技术附属医师")
    @Size(min = 1)
    private ArrayList<String> ids;
}
