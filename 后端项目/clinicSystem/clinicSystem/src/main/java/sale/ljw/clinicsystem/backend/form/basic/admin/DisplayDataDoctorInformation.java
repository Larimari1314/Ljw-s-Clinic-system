package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "医技表中关联医生数据")
public class DisplayDataDoctorInformation {
    @ApiModelProperty(value = "医生id")
    private String id;

    @ApiModelProperty(value = "医生姓名")
    private String name;
}
