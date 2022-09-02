package sale.ljw.clinicsystem.backend.form.basic.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找当前预约信息")
public class FindAllCurrentAppointmentByPatient {
    @ApiModelProperty(value = "当前页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "患者id")
    @NotBlank(message = "患者id不得为空")
    private String patientId;
    @ApiModelProperty(value = "医生名称")
    private String doctorName;
}
