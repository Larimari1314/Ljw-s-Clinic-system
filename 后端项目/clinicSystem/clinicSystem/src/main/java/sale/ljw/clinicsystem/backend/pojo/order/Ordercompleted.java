package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName orderCompleted
 */
@TableName(value ="orderCompleted")
@Data
public class Ordercompleted implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private String id;

    /**
     * 支付编号
     */
    private String payment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}