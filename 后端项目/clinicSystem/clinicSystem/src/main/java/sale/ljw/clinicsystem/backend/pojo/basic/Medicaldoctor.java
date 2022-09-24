package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.*;

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


    @Version
    private Integer optimisticLocking;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}