package sale.ljw.clinic_administrator_background_system.service.doctor;


import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public interface DoctorInformationService {
    public abstract String findAllDoctor(Integer page);

    public abstract String findByNameDoctor(String doctorName, Integer page);

    public abstract String removeDoctorById(String id);

    public abstract String batchRemoveDoctor(String ids);

    public abstract String addOneDoctor(Doctor_information doctor_information);

    public abstract String editDoctor(Doctor_information doctor_information);

    public abstract ByteArrayOutputStream doctorExportData(String url);

    public abstract ByteArrayOutputStream doctorTemplateDownload(String url);

    public abstract Message analyseFile(String fileName);

    public abstract String findDoctorByDepartment(String did);

    public abstract String findByNameList(String did,String name);
}
