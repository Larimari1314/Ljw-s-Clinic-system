package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName adminInformation
 */
@TableName(value ="adminInformation")
@Data
public class Admininformation implements Serializable {
    /**
     * 管理员id
     */
    @TableId
    private String id;

    /**
     * 管理员头像
     */
    private String avatar;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员年龄
     */
    private Integer age;

    /**
     * 管理员生日
     */
    private Date birthday;

    /**
     * 管理员性别
     */
    private String sex;

    /**
     * 管理员身份证号
     */
    private String identitynumber;

    /**
     * 家庭住址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}