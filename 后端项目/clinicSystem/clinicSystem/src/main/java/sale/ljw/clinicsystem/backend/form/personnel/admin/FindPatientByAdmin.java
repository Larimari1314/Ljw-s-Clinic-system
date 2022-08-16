package sale.ljw.clinicsystem.backend.form.personnel.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找患者")
public class FindPatientByAdmin {
    @ApiModelProperty(value = "页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "性别")
    private String sex;
}
