package sale.ljw.clinic_administrator_background_system.dao.doctor;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;

import java.util.ArrayList;
@Mapper
@Repository
public interface DoctorInformationDao {
    /**
     * 查找全部数据
     * @return
     */
    public abstract ArrayList<Doctor_information> findAllDoctor();

    /**
     * 根据名字模糊查找医生
     * @param doctorName
     * @return
     */
    public abstract ArrayList<Doctor_information> findByNameDoctor(String doctorName);

    /**
     * 根据id删除医生
     * @param id
     * @return
     */
    public abstract Integer removeDoctorById(String id);

    /**
     * 根据id批量删除医生
     * @param ids
     * @return
     */
    public abstract Integer batchRemoveDoctor(ArrayList<String> ids);

    /**
     * 添加单独医生
     * @param doctor_information
     * @return
     */
    public abstract Integer addOneDoctor(Doctor_information doctor_information);

    /**
     * 添加用户
     * @return
     */
    public abstract Integer editDoctor(Doctor_information doctor_information);

    /**
     * 根据列表存储医生
     */
    public abstract Integer addListDoctor(ArrayList<Doctor_information> doctorInformationList);

    /**
     * 根据部门did查询
     *
     * @param did
     * @return
     */
    public abstract ArrayList<Doctor_information> findDoctorByDepartment(String did);

    /**
     *
     * @param id
     * @return
     */
    public abstract Doctor_information findById(String id);

    /**
     * 根据名称和id查找
     * @param did
     * @param name
     * @return
     */
    public abstract ArrayList<Doctor_information> findByNameList(String did, String name);
}
