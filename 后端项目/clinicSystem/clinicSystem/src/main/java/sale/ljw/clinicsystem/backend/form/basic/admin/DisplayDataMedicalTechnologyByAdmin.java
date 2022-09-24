package sale.ljw.clinicsystem.backend.form.basic.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@ApiModel(value = "医技页面中显示数据")
public class DisplayDataMedicalTechnologyByAdmin {
    @ApiModelProperty(value = "医技id")
    private String id;

    @ApiModelProperty(value = "医技图片")
    private String introduce;

    @ApiModelProperty(value = "医技名称")
    private String technologyname;

    @ApiModelProperty(value = "医技分类")
    private String projectName;

    @ApiModelProperty(value = "医技分类id")
    private String projectCoding;

    @ApiModelProperty(value = "医生基本信息")
    private ArrayList<DisplayDataDoctorInformation> doctor;
}
