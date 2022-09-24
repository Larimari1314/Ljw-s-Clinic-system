package sale.ljw.clinicsystem.backend.form.basic.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "查找医技表单")
public class MedicalTechnologyAppointmentForm {
    @ApiModelProperty(value = "预约检查项")
    @NotNull(message = "预约检查项不得为空")
    private Integer medicalid;

    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorid;

    @ApiModelProperty(value = "预约id")
    private String reserveid;

    @ApiModelProperty(value = "预约日期")
    @NotNull(message = "预约日期")
    private Date reservedate;

    @ApiModelProperty(value = "预约时间")
    @NotBlank(message = "预约时间不得为空")
    private String reservetime;

}
