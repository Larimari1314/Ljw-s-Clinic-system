package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName doctorLogin
 */
@TableName(value ="doctorLogin")
@Data
public class Doctorlogin implements Serializable {
    /**
     * 医生id
     */
    @TableId
    @TableField(value = "id")
    private String id;

    /**
     * 医生登录名
     */
    @TableField(value = "loginName")
    private String loginname;

    /**
     * 登录号码
     */
    @TableField(value = "loginPhone")
    private String loginphone;

    /**
     * 登录邮箱
     */
    @TableField(value = "loginEmail")
    private String loginemail;

    /**
     * 登陆密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 登陆权限
     */
    @TableField(value = "permission")
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Doctorlogin(String id, String loginname, String loginphone, String loginemail, String password, Integer permission) {
        this.id = id;
        this.loginname = loginname;
        this.loginphone = loginphone;
        this.loginemail = loginemail;
        this.password = password;
        this.permission = permission;
    }

    public Doctorlogin() {
    }
}