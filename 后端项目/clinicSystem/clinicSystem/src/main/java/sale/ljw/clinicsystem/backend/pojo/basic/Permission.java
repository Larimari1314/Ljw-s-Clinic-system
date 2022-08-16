package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName permission
 */
@TableName(value ="permission")
@Data
public class Permission implements Serializable {
    /**
     * 权限id
     */
    @TableId
    private Integer id;

    /**
     * 访问url
     */
    private String url;

    /**
     * 访问boolean
     */
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}