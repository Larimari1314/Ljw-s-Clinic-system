package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 医生基本信息表
 */
@TableName(value ="doctorInformation")
@Data
public class Doctorinformation implements Serializable {
    /**
     * 医生id
     */
    @TableId
    private String id;

    /**
     * 医生头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 医生姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 医生年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 医生生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 医生性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 医生身份证号
     */
    @TableField(value = "identityNumber")
    private String identitynumber;

    /**
     * 科室id
     */
    @TableField(value = "did")
    private String did;

    /**
     * 薪资
     */
    @TableField(value = "salary")
    private Double salary;

    /**
     * 挂号级别
     */
    @TableField(value = "registereId")
    private String registereid;

    /**
     * 家庭住址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(value = "doctorDelete")
    private Integer doctordelete;

}