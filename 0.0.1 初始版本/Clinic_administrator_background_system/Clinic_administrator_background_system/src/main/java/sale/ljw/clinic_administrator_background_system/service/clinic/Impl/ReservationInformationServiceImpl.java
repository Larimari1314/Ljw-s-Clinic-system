package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.clinic.ReservationInformationDao;
import sale.ljw.clinic_administrator_background_system.dao.doctor.DoctorInformationDao;
import sale.ljw.clinic_administrator_background_system.dao.patient.PatientInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.ReservationInformation;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;
import sale.ljw.clinic_administrator_background_system.service.clinic.ReservationInformationService;

import java.util.ArrayList;

@Service
public class ReservationInformationServiceImpl implements ReservationInformationService {
    @Autowired
    private ReservationInformationDao reservationInformationDao;
    @Autowired
    private PatientInformationDao patientInformationDao;

    @Autowired
    private DoctorInformationDao doctorInformationDao;

    @Override
    public String findByDid(String did) {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "", reservationInformationDao.findByDid(did));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            WebMessage webMessage = new WebMessage("获取预约信息失败，请稍后重试", e.getMessage());
            return JSON.toJSONString(webMessage);
        }
    }

    @Override
    public String findAll(Integer page) {
        try {
            PageHelper.startPage(page, 20);
            ArrayList<ReservationInformation> allTime = reservationInformationDao.findAll();
            PageInfo pageInfo = new PageInfo(allTime);
            WebMessage webMessage = new WebMessage(Code.success_code, "", pageInfo);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("查找全部失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String deleteReservation(String id) {
        try {
            Integer deleteReservation = reservationInformationDao.deleteReservation(id);
            if (deleteReservation == 1) {
                WebMessage webMessage = new WebMessage(Code.success_code, "撤销成功", deleteReservation);
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                WebMessage webMessage = new WebMessage("撤销失败，请稍后重试", "");
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
            }
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("患者就诊结束，不可撤销", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String findById(String id) {
        try {
            WebMessage webMessage=new WebMessage(Code.success_code,"",reservationInformationDao.findById(id));
            return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
            WebMessage webMessage=new WebMessage("查找失败，请稍后重试",e.getMessage());
            return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String findByName(String name,Integer page) {
        name="%"+name+"%";
        //根据名称在医生和患者中查询姓名，一旦匹配就输
        try {
            ArrayList<Patient_information> patient = patientInformationDao.findByNamePatient(name);
            ArrayList<Doctor_information> doctor = doctorInformationDao.findByNameDoctor(name);
            WebMessage webMessage=new WebMessage();
            webMessage.setCode(Code.success_code);
            if(doctor==null||doctor.size()==0){
                if(patient==null||patient.size()==0){
                    webMessage.setCode(Code.known_error_code);
                    webMessage.setMessage("并未查找到字段中存在“"+name.substring(1, name.length()-1)+"”的医生患者");
                    return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
                }if(patient!=null||patient.size()!=0){
                    webMessage.setMessage("并未查找到字段中存在“"+name.substring(1, name.length()-1)+"”该名称的医生,此处显示所查找的患者");
                }
            }else {
                if(patient==null||patient.size()==0){
                    webMessage.setMessage("并未查找到字段中存在“"+name.substring(1, name.length()-1)+"”名称的患者,此处显示所查找的医生");
                }
                if(patient!=null||patient.size()!=0){
                    webMessage.setMessage("以下显示名字字段中存在“"+name.substring(1, name.length()-1)+"”名称的医生、患者");
                }
            }
            //将查找的id存储
            ArrayList<String> id=new ArrayList<>();
            if(patient!=null){
                for (Patient_information patient_information : patient) {
                    id.add(patient_information.getId());
                }
            }
            if(doctor!=null){
                for (Doctor_information doctor_information : doctor) {
                    id.add(doctor_information.getId());
                }
            }
            PageHelper.startPage(page, 20);
            ArrayList<ReservationInformation> byName = reservationInformationDao.findByName(id);
            PageInfo pageInfo = new PageInfo(byName);
            webMessage.setData(pageInfo);
            return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
         WebMessage webMessage=new WebMessage("出错了，请稍后重试","");
         return JSON.toJSONString(webMessage,SerializerFeature.DisableCircularReferenceDetect);
        }
    }

}
