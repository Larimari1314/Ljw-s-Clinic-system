package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 患者信息表
 */
@TableName(value ="patientInformation")
@Data
public class Patientinformation implements Serializable {
    /**
     * 患者id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 患者头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 患者姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 患者年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 患者生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 患者性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 患者身份证号
     */
    @TableField(value = "identityNumber")
    private String identitynumber;

    /**
     * 家庭住址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 逻辑删除
     */
    @TableLogic(value = "doctorDelete")
    private Integer doctordelete;

}