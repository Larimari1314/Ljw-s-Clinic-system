package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dutyTime
 */
@TableName(value ="dutyTime")
@Data
public class Dutytime implements Serializable {
    /**
     * 值班id
     */
    @TableId
    private String id;

    /**
     * 起始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}