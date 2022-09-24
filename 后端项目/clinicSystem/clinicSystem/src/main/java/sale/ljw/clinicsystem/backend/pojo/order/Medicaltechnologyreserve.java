package sale.ljw.clinicsystem.backend.pojo.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName medicalTechnologyReserve
 */
@TableName(value ="medicalTechnologyReserve")
@Data
public class Medicaltechnologyreserve implements Serializable {
    /**
     * 医技id
     */
    @TableId
    private String id;

    /**
     * 医生id
     */
    private String doctorid;

    /**
     * 患者id
     */
    private String patientid;

    /**
     * 预约信息表
     */
    private String reserveid;

    /**
     * 医技id
     */
    private Integer medicalid;

    /**
     * 当前状态
     */
    private String status;

    /**
     * 检查报告
     */
    private String inspectionreport;

    /**
     * 预约日期
     */
    private Date reservedate;

    /**
     * 预约班次
     */
    private String reservetime;

    /**
     * 排队编号
     */
    private Integer queuenumber;

    /**
     * 检查部位
     */
    private String checkparts;

    /**
     * 预约时间
     */
    private Date appointmenttime;
    /*
    * 价钱
    * */
    private Double money;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}