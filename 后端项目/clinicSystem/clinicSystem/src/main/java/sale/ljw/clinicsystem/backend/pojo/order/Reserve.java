package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName reserve
 */
@TableName(value ="reserve")
@Data
public class Reserve implements Serializable {
    /**
     * 预约d
     */
    @TableId
    private String id;

    /**
     * 医生id
     */
    private String doctorid;

    /**
     * 患者id
     */
    private String patientid;

    /**
     * 预约状态
     */
    private Integer state;

    /**
     * 预约时间
     */
    private Date reservetime;

    /**
     * 提醒次数
     */
    private Integer numberreminders;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}