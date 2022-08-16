package sale.ljw.clinicsystem.backend.form.order.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "查找预约单")
public class FindReserveNotViewedByAdmin {
    @NotNull
    @ApiModelProperty(value = "当前页数")
    private Integer page;
    @ApiModelProperty(value = "患者姓名")
    private String patientName;
    @ApiModelProperty(value = "医生姓名")
    private String doctorName;
    @ApiModelProperty(value = "科室id")
    private String did;
    @ApiModelProperty(value = "会诊级别")
    private String registereId;
}
