package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 药品表
 */
@TableName(value ="drug")
@Data
public class Drug implements Serializable {
    /**
     * 药品id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 药品封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 药品名称
     */
    @TableField(value = "drugName")
    private String drugname;

    /**
     * 药品规格
     */
    @TableField(value = "specification")
    private String specification;

    /**
     * 当前数量
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 单价
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 逻辑删除
     */
    @TableLogic(value = "drugDelete")
    private Integer drugdelete;

    /**
     * 版本号
     */
    @TableField(value = "version")
    @Version
    private Integer version;

    /**
     * 对外启用
     */
    @TableField(value = "enable")
    private Integer enable;

}