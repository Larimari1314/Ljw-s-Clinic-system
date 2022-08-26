package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "重新预约患者")
public class ConfirmAppointmentByDoctor {
    @ApiModelProperty(value = "预约医生id")
    @NotBlank(message = "预约医生id不得为空")
    private String doctorid;
    @ApiModelProperty(value = "患者id")
    @NotBlank(message = "患者id不得为空")
    private String patientid;
    @ApiModelProperty(value = "预约时间")
    @NotNull(message = "预约时间不得为空")
    private Date reservetime;
}
