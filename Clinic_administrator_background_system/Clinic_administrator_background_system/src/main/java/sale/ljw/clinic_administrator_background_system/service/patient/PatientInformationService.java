package sale.ljw.clinic_administrator_background_system.service.patient;

import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;

import java.io.ByteArrayOutputStream;

public interface PatientInformationService {
    public abstract String findAllPatient(Integer page);

    public abstract String findByNamePatient(String patientName, Integer page);

    public abstract String removePatientById(String id);

    public abstract String batchRemovePatient(String ids);

    public abstract String addOnePatient(Patient_information patient_information);

    public abstract String editPatient(Patient_information patient_information);
    public abstract ByteArrayOutputStream patientExportData(String url);
    public abstract ByteArrayOutputStream patientTemplateDownload(String url);
    public abstract Message analyseFile(String fileName);
}
