package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName orderDrug
 */
@TableName(value ="orderDrug")
@Data
public class Orderdrug implements Serializable {
    /**
     * 订单表id
     */
    private String orderid;

    /**
     * 药品id
     */
    private String drugid;

    /**
     * 药品数量
     */
    private Integer number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}