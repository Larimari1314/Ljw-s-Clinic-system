package sale.ljw.clinic_administrator_background_system.dao.patient;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;

import java.util.ArrayList;

@Mapper
@Repository
public interface PatientInformationDao {
    /**
     * 查找全部数据
     * @return
     */
    public abstract ArrayList<Patient_information> findAllPatient();

    /**
     * 根据名字模糊查找患者
     * @param patientName
     * @return
     */
    public abstract ArrayList<Patient_information> findByNamePatient(String patientName);

    /**
     * 根据id删除患者
     * @param id
     * @return
     */
    public abstract Integer removePatientById(String id);

    /**
     * 根据id批量删除患者
     * @param ids
     * @return
     */
    public abstract Integer batchRemovePatient(ArrayList<String> ids);

    /**
     * 添加单独患者
     * @param patient_information
     * @return
     */
    public abstract Integer addOnePatient(Patient_information patient_information);

    /**
     * 添加用户
     * @return
     */
    public abstract Integer editPatient(Patient_information patient_information);

    /**
     * 根据列表存储患者
     */
    public abstract Integer addListPatient(ArrayList<Patient_information> patientInformationList);

    public abstract Patient_information findById(String id);
}
