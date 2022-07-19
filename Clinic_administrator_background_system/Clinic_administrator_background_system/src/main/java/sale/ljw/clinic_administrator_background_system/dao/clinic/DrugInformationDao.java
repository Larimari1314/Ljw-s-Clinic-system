package sale.ljw.clinic_administrator_background_system.dao.clinic;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.Drug_information;

import java.util.ArrayList;

@Mapper
@Repository
public interface DrugInformationDao {
    /**
     * 查找全部数据
     *
     * @return
     */
    public abstract ArrayList<Drug_information> findAllDrug();

    /**
     * 根据名字模糊查找药品
     *
     * @param drugName
     * @return
     */
    public abstract ArrayList<Drug_information> findByNameDrug(String drugName);


    /**
     * 添加单独医生
     *
     * @param drug_information
     * @return
     */
    public abstract Integer addOneDrug(Drug_information drug_information);

    /**
     * 修改药品信息
     *
     * @param drug_information
     * @return
     */
    public abstract Integer editDrug(Drug_information drug_information);

    /**
     * 根据excel列表添加药品信息
     *
     * @param drug_information
     * @return
     */
    public abstract Integer addListDrug(ArrayList<Drug_information> drug_information);

    /**
     * 根据前端开关传来的数值修改是否对用户显示
     * @param drug_information
     * @return
     */
    public abstract Integer exitDrugExhibit(Drug_information drug_information);

    public abstract Drug_information findByIdDrug(String id);
}
