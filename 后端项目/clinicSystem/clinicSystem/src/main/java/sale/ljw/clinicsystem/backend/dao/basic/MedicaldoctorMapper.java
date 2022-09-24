package sale.ljw.clinicsystem.backend.dao.basic;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.clinicsystem.backend.form.basic.patient.MedicalTechnologyAppointmentForm;
import sale.ljw.clinicsystem.backend.pojo.basic.Medicaldoctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86155
* @description 针对表【medicalDoctor】的数据库操作Mapper
* @createDate 2022-09-22 19:06:56
* @Entity sale.ljw.clinicsystem.backend.pojo.basic.Medicaldoctor
*/
@Mapper
@Repository
public interface MedicaldoctorMapper extends BaseMapper<Medicaldoctor> {
    Integer findMedicalResultCount(MedicalTechnologyAppointmentForm medicalTechnology);
}




