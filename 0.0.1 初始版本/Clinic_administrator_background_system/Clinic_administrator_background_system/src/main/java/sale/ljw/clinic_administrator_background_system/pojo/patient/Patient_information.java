package sale.ljw.clinic_administrator_background_system.pojo.patient;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者信息表
 */
public class Patient_information implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 真实姓名
     */
    private String patientName;
    /**
     * 网站昵称
     */
    private String webName;
    /**
     * 联系电话
     */
    private String patientPhone;
    /**
     * 电子邮件
     */
    private String patientEmail;
    /**
     * 身份证号码
     */
    private String identityNumber;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像路径
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private int patientSex;
    /**
     * 生日日期
     */
    private Date patientBirth;
    /**
     * 家庭住址
     */
    private String patientAddr;
    /**
     * 年龄
     */
    private int patientAge;

    @Override
    public String toString() {
        return "Patient_information{" +
                "id='" + id + '\'' +
                ", patientName='" + patientName + '\'' +
                ", webName='" + webName + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", password='" + password + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", patientSex=" + patientSex +
                ", patientBirth=" + patientBirth +
                ", patientAddr='" + patientAddr + '\'' +
                ", patientAge=" + patientAge +
                '}';
    }

    public int getPatientAge() {
        return patientAge;
    }

    public Patient_information(String id, String patientName, String webName, String patientPhone, String patientEmail, String identityNumber, String password, String avatarUrl, int patientSex, Date patientBirth, String patientAddr, int patientAge) {
        this.id = id;
        this.patientName = patientName;
        this.webName = webName;
        this.patientPhone = patientPhone;
        this.patientEmail = patientEmail;
        this.identityNumber = identityNumber;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.patientSex = patientSex;
        this.patientBirth = patientBirth;
        this.patientAddr = patientAddr;
        this.patientAge = patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public Patient_information() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(int patientSex) {
        this.patientSex = patientSex;
    }

    public Date getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(Date patientBirth) {
        this.patientBirth = patientBirth;
    }

    public String getPatientAddr() {
        return patientAddr;
    }

    public void setPatientAddr(String patientAddr) {
        this.patientAddr = patientAddr;
    }
}
