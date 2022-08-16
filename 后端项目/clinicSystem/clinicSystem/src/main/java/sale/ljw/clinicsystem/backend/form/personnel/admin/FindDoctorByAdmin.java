package sale.ljw.clinicsystem.backend.form.personnel.admin;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员查找医生封装类")
public class FindDoctorByAdmin {
    @ApiModelProperty(value = "页码")
    @NotNull
    private Integer page;
    @ApiModelProperty(value = "医生姓名")
    private String name;
    @ApiModelProperty(value = "医生性别")
    private String sex;
    @ApiModelProperty(value = "科室编码")
    private String did;
    @ApiModelProperty(value = "挂号级别编码")
    private String registereId;
}
