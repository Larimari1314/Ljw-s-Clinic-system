package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.clinic_administrator_background_system.dao.clinic.AppointmentTimeDao;
import sale.ljw.clinic_administrator_background_system.dao.clinic.ReservationInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTime;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AppointmentTimeWeb;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.ReservationInformation;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.AppointmentTimeService;
import sale.ljw.clinic_administrator_background_system.utils.timeUtils.TimeFormatSeparate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentTimeServiceImpl implements AppointmentTimeService {
    @Autowired
    private AppointmentTimeDao appointmentTimeDao;
    @Autowired
    private ReservationInformationDao reservationInformationDao;

    @Override
    public String findAllTime() {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "", appointmentTimeDao.findAllTime());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(new WebMessage("查找医生在任时间失败，请稍后重试", e.getMessage()));
        }
    }

    @Override
    public String editAppointmentTime(AppointmentTime appointmentTime) {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "", appointmentTimeDao.findAllTime());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            return JSON.toJSONString(new WebMessage("修改医生值班时间失败，请稍后重试", e.getMessage()));
        }
    }

    @Override
    public String findTimeMorning(String did) {
        try {
            AppointmentTime timeMorning = appointmentTimeDao.findTimeMorning();
            List<String> intervalTimeList = TimeFormatSeparate.getIntervalTimeList(timeMorning.getStartTime(), timeMorning.getEndTime(), timeMorning.getStep());
            ArrayList<ReservationInformation> reservationInformation = reservationInformationDao.findByDid(did);
            List<String> appointmentList = new ArrayList<>();
            for (ReservationInformation information : reservationInformation) {
                appointmentList.add(information.getAppointment());
            }
            ArrayList<AppointmentTimeWeb> appointmentTimeWebs = new ArrayList<>();
            for (String time : intervalTimeList) {
                boolean sale = false;
                AppointmentTimeWeb appointmentTimeWeb = new AppointmentTimeWeb();
                appointmentTimeWeb.setTime(time);
                for (String time2 : appointmentList) {
                    if (time2.equals(time)) {
                        sale = true;
                        break;
                    }
                }
                if (sale) {
                    appointmentTimeWeb.setDisable(true);
                } else {
                    appointmentTimeWeb.setDisable(false);
                }
                appointmentTimeWebs.add(appointmentTimeWeb);
            }
            WebMessage webMessage = new WebMessage(Code.success_code, "", appointmentTimeWebs);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(new WebMessage("查找医生上午值班时间失败，请稍后重试", e.getMessage()));
        }
    }

    @Override
    public String findTimeAfternoon(String did) {
        try {
            AppointmentTime timeMorning = appointmentTimeDao.findTimeAfternoon();
            List<String> intervalTimeList = TimeFormatSeparate.getIntervalTimeList(timeMorning.getStartTime(), timeMorning.getEndTime(), timeMorning.getStep());
            ArrayList<ReservationInformation> reservationInformation = reservationInformationDao.findByDid(did);
            List<String> appointmentList = new ArrayList<>();
            for (ReservationInformation information : reservationInformation) {
                appointmentList.add(information.getAppointment());
            }
            ArrayList<AppointmentTimeWeb> appointmentTimeWebs = new ArrayList<>();
            for (String time : intervalTimeList) {
                boolean sale = false;
                AppointmentTimeWeb appointmentTimeWeb = new AppointmentTimeWeb();
                appointmentTimeWeb.setTime(time);
                for (String time2 : appointmentList) {
                    if (time2.equals(time)) {
                        sale = true;
                        break;
                    }
                }
                if (sale) {
                    appointmentTimeWeb.setDisable(true);
                } else {
                    appointmentTimeWeb.setDisable(false);
                }
                appointmentTimeWebs.add(appointmentTimeWeb);
            }
            WebMessage webMessage = new WebMessage(Code.success_code, "", appointmentTimeWebs);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(new WebMessage("查找医生上午值班时间失败，请稍后重试", e.getMessage()));
        }
    }
}
