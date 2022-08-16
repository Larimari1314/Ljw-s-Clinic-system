package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName doctorDuty
 */
@TableName(value ="doctorDuty")
@Data
public class Doctorduty implements Serializable {
    /**
     * 医生id
     */
    @TableId
    private String id;

    /**
     * 值班id
     */
    private String dutyid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}