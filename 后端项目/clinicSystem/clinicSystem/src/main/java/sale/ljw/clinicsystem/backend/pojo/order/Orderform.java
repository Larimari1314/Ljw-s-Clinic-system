package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName OrderForm
 */
@TableName(value ="OrderForm")
@Data
public class Orderform implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private String id;

    /**
     * 最后操作时间
     */
    private Date operatingtime;

    /**
     * 当前状态
     */
    private String state;

    /**
     * 当前版本号
     */
    private Integer version;

    /**
     * 删除逻辑
     */
    @TableLogic
    private Integer orderdelete;

    /**
     * 订单钱数
     */
    private Double orderprice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}