package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "修改医技基本信息")
public class EditMedicalClassificationByAdmin {
    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不得为空")
    private String id;

    @ApiModelProperty(value = "医技名称")
    @NotBlank(message = "医技名称不得为空")
    private String name;

    @ApiModelProperty(value = "最大人数")
    @NotNull(message = "最大人数不得为空")
    private Integer maxNumber;

    @ApiModelProperty(value = "money")
    @NotNull(message = "金额不得为空")
    private Double money;
}
