package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName department
 */
@TableName(value ="department")
@Data
public class Department implements Serializable {
    /**
     * 科室id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 科室名称
     */
    private String name;

    /**
     * 科室介绍
     */
    private String introduce;

    /**
     * 科室封面
     */
    private String cover;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}