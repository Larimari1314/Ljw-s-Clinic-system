package sale.ljw.clinicsystem.backend.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName adminLogin
 */
@TableName(value ="adminLogin")
@Data
public class Adminlogin implements Serializable {
    /**
     * 管理员id
     */
    @TableId
    private String id;

    /**
     * 管理员登录名
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
     * 登陆权限
     */
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}