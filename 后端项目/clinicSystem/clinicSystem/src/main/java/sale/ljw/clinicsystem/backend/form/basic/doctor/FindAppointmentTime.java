package sale.ljw.clinicsystem.backend.form.basic.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "获取预约时间")
public class FindAppointmentTime {
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorId;

    @ApiModelProperty(value = "预约日期")
    @NotNull(message = "预约日期不得为空")
    private Date date;
    @ApiModelProperty(value = "患者id")
    @NotBlank(message = "患者id不得为空")
    private String patientId;
}
