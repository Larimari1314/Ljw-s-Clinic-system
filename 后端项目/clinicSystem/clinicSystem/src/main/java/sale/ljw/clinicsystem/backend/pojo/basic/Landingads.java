package sale.ljw.clinicsystem.backend.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName landingAds
 */
@TableName(value ="landingAds")
@Data
public class Landingads implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 广告图片
     */
    private String imgurl;

    /**
     * 链接地址
     */
    private String ads;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}