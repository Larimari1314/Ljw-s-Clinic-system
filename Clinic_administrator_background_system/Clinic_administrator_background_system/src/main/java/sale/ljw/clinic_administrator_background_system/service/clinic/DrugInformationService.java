package sale.ljw.clinic_administrator_background_system.service.clinic;

import sale.ljw.clinic_administrator_background_system.pojo.clinic.Drug_information;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public interface DrugInformationService {
    /**
     * 查找全部药品
     *
     * @return
     */
    public abstract String findAllDrug(Integer page);

    /**
     * 根据药品名称查找
     *
     * @param drugName
     * @return
     */
    public abstract String findByNameDrug(Integer page,String drugName);

    /**
     * 添加药品
     *
     * @param drug_information
     * @return
     */
    public abstract String addOneDrug(Drug_information drug_information,String fileName);

    /**
     * 修改药品信息
     *
     * @param drug_information
     * @return
     */
    public abstract String editDrug(Drug_information drug_information);


    ByteArrayOutputStream DrugExportData(String url);

    ByteArrayOutputStream DrugTemplateDownload(String url);

    Message analyseFile(String fileName);

    public abstract String exitDrugExhibit(Drug_information drug_information);
}
