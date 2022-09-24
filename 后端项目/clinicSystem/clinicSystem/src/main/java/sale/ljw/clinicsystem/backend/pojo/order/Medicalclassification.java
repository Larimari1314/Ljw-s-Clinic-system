package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName medicalClassification
 */
@TableName(value ="medicalClassification")
@Data
public class Medicalclassification implements Serializable {
    /**
     * 小类id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类别id
     */
    private String medicalid;

    /**
     * 价钱
     */
    private Double money;

    /**
     * 时间段内最大接诊人数
     */
    private Integer maxnumber;

    /**
     * 名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}