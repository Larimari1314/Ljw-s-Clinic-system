package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName patientLogin
 */
@TableName(value ="patientLogin")
@Data
public class Patientlogin implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private String id;

    /**
     * 用户登录名
     */
    private String loginname;

    /**
     * 登录号码
     */
    private String loginphone;

    /**
     * 登录邮箱
     */
    private String loginemail;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 登录权限
     */
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Patientlogin() {
    }

    public Patientlogin(String id, String loginname, String loginphone, String loginemail, String password, Integer permission) {
        this.id = id;
        this.loginname = loginname;
        this.loginphone = loginphone;
        this.loginemail = loginemail;
        this.password = password;
        this.permission = permission;
    }
}