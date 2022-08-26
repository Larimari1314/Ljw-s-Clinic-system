package sale.ljw.clinicsystem.backend.form.order.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "发药")
public class DispensingMedicineByDoctor {
    @ApiModelProperty(value = "医生id")
    @NotBlank(message = "医生id不得为空")
    private String doctorId;

    @ApiModelProperty(value = "预约id")
    @NotBlank(message = "预约id不得为空")
    private String reserveId;

    @ApiModelProperty(value = "支付编号")
    @NotBlank(message = "支付编号不得为空")
    @Length(min = 30,max = 30)
    private String payment;
}
