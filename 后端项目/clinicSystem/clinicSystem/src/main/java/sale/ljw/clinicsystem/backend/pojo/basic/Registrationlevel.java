package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName registrationLevel
 */
@TableName(value ="registrationLevel")
@Data
public class Registrationlevel implements Serializable {
    /**
     * 挂号级别id
     */
    @TableId
    private String id;

    /**
     * 挂号名称
     */
    private String name;

    /**
     * 挂号金额
     */
    private Double price;

    /**
     * 版本号
     */

    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}