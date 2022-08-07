package sale.ljw.clinic_administrator_background_system.pojo.doctor;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生信息表
 */
public class Doctor_information implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 医生联系电话
     */
    private String doctorPhone;
    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 登陆密码
     */
    private String doctorPassword;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 月薪
     */
    private String salary;
    /**
     * 医生年龄
     */
    private Integer doctorAge;
    /**
     * 医生性别
     */
    private String doctorSex;
    /**
     * 医生生日
     */
    private Date doctorBirth;
    /**
     * 医生家庭住址
     */
    private String doctorAddr;
    /**
     * 所在科目id
     */
   private Department department;

    @Override
    public String toString() {
        return "Doctor_information{" +
                "id='" + id + '\'' +
                ", doctorPhone='" + doctorPhone + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorPassword='" + doctorPassword + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", salary='" + salary + '\'' +
                ", doctorAge=" + doctorAge +
                ", doctorSex='" + doctorSex + '\'' +
                ", doctorBirth=" + doctorBirth +
                ", doctorAddr='" + doctorAddr + '\'' +
                ", department=" + department +
                '}';
    }

    public Doctor_information() {
    }

    public Doctor_information(String id, String doctorPhone, String doctorName, String doctorPassword, String avatarUrl, String salary, Integer doctorAge, String doctorSex, Date doctorBirth, String doctorAddr, Department department) {
        this.id = id;
        this.doctorPhone = doctorPhone;
        this.doctorName = doctorName;
        this.doctorPassword = doctorPassword;
        this.avatarUrl = avatarUrl;
        this.salary = salary;
        this.doctorAge = doctorAge;
        this.doctorSex = doctorSex;
        this.doctorBirth = doctorBirth;
        this.doctorAddr = doctorAddr;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Integer getDoctorAge() {
        return doctorAge;
    }

    public void setDoctorAge(Integer doctorAge) {
        this.doctorAge = doctorAge;
    }

    public String getDoctorSex() {
        return doctorSex;
    }

    public void setDoctorSex(String doctorSex) {
        this.doctorSex = doctorSex;
    }

    public Date getDoctorBirth() {
        return doctorBirth;
    }

    public void setDoctorBirth(Date doctorBirth) {
        this.doctorBirth = doctorBirth;
    }

    public String getDoctorAddr() {
        return doctorAddr;
    }

    public void setDoctorAddr(String doctorAddr) {
        this.doctorAddr = doctorAddr;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
