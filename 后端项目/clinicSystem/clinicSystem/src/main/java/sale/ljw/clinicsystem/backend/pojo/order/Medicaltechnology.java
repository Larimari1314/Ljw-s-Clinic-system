package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName medicalTechnology
 */
@TableName(value ="medicalTechnology")
@Data
public class Medicaltechnology implements Serializable {
    /**
     * 医技id
     */
    @TableId
    private String id;

    /**
     * 科室id
     */
    private String departmentid;

    /**
     * 医技大类
     */
    private String projectname;

    /**
     * 分类图片
     */
    private String introduce;

    /**
     * 医技名称
     */
    private String technologyname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}