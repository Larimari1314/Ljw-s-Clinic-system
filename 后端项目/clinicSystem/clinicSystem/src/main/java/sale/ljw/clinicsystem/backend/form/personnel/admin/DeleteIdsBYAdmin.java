package sale.ljw.clinicsystem.backend.form.personnel.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel(value = "根据id删除列表")
public class DeleteIdsBYAdmin {
    @ApiModelProperty("id列表")
    @Size(min = 1)
    private List<String> ids;
}
