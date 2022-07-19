package sale.ljw.clinic_administrator_background_system.service.doctor;

import sale.ljw.clinic_administrator_background_system.pojo.doctor.Department;

import java.util.ArrayList;

public interface DepartmentService {

    public abstract String findAllDepartment();

    public abstract String findById(String id);
}
