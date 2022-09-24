package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "医技预约查询")
public class FindAllReserveMedicalTechnology {
    @ApiModelProperty(value = "患者姓名")
    private String patientName;
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "检查部位")
    private String checkParts;
}
