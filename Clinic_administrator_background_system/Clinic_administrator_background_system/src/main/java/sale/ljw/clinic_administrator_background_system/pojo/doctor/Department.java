package sale.ljw.clinic_administrator_background_system.pojo.doctor;

import java.io.Serializable;

public class Department implements Serializable {
    private String id;
    private String departmentName;
    private String imgUrl;
    private String departmentIntroduce;

    public Department() {
    }

    public Department(String id, String departmentName, String imgUrl, String departmentIntroduce) {
        this.id = id;
        this.departmentName = departmentName;
        this.imgUrl = imgUrl;
        this.departmentIntroduce = departmentIntroduce;
    }

    public String getDepartmentIntroduce() {
        return departmentIntroduce;
    }

    public void setDepartmentIntroduce(String departmentIntroduce) {
        this.departmentIntroduce = departmentIntroduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
