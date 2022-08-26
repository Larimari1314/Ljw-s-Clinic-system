package sale.ljw.clinicsystem.backend.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.clinicsystem.backend.form.basic.doctor.*;
import sale.ljw.clinicsystem.backend.form.order.admin.FindReserveNotViewedByAdmin;
import sale.ljw.clinicsystem.backend.form.order.doctor.*;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;

/**
 * @author 86155
 * @description 针对表【reserve】的数据库操作Service
 * @createDate 2022-08-07 16:12:15
 */
public interface ReserveService extends IService<Reserve> {
    public abstract String findReserveNotViewed(FindReserveNotViewedByAdmin reserve);

    public abstract String cancelAppointment(DeleteIdsBYAdmin ids);

    public abstract String cancelAppointmentViews(FindReserveNotViewedByAdmin reserve);

    public abstract String resumeAppointment(DeleteIdsBYAdmin ids);

    public abstract String historyAppointment(FindReserveByDoctor reserve);

    public abstract String checkOrder(CheckOrderByDoctor order);

    public abstract String checkOrderDrug(String id);

    public abstract String getAppointmentTime(FindAppointmentTime appointment);

    public abstract String getOnDuty(String id);

    public abstract String confirmAppointment(ConfirmAppointmentByDoctor confirmAppointment);

    public abstract String findCurrentAppointments(FindReserveByDoctor reserve);

    public abstract String remindPatientNumber(RemindPatientByDoctor remindPatient);

    public abstract String searchMedicinesRemotely(String drugName);

    public abstract String findDrugByDrugName(OrderFindDrugListByDoctor drugName);

    public abstract String addDrugByPatient(AddDrugByPatient addDrug);

    public abstract String removeDrug(RemoveDrugByDoctor drug);

    public abstract String endAppointment(String orderId);

    public abstract String findDispensingInterface(FindDispensingInterfaceByDoctor findDispensing);

    public abstract String dispensingMedicine(DispensingMedicineByDoctor checkOrder);
}
