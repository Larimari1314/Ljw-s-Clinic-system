package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName medicalDoctor
 */
@TableName(value ="medicalDoctor")
@Data
public class Medicaldoctor implements Serializable {
    /**
     * 
     */
    private String medicalid;

    /**
     * 
     */
    private String doctorid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}