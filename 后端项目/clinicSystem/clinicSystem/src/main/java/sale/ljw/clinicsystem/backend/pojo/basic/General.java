package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName general
 */
@TableName(value ="general")
@Data
public class General implements Serializable {
    /**
     * 常规项编码
     */
    private String coding;

    /**
     * 常规项值
     */
    private String value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}