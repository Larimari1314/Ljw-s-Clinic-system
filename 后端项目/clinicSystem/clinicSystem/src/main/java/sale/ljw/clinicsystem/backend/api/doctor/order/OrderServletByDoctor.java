package sale.ljw.clinicsystem.backend.api.doctor.order;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinicsystem.backend.form.basic.doctor.*;
import sale.ljw.clinicsystem.backend.form.order.doctor.*;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.service.order.ReserveService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/orderByDoctor")
public class OrderServletByDoctor {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation(value = "获取全部历史订单")
    @PostMapping("/historyAppointment")
    public String historyAppointment(@RequestBody @Valid FindReserveByDoctor reserve) {
        return reserveService.historyAppointment(reserve);
    }

    @ApiOperation(value = "查看当前订单")
    @PostMapping("/checkOrder")
    public String checkOrder(@RequestBody @Valid CheckOrderByDoctor order) {
        return reserveService.checkOrder(order);
    }

    @ApiOperation(value = "查看当前订单药品")
    @GetMapping("/checkOrderDrug/{id}")
    public String checkOrderDrug(@PathVariable String id) {
        return reserveService.checkOrderDrug(id);
    }

    @ApiOperation(value = "获取医生当前值班状态")
    @GetMapping("/getOnDuty/{id}")
    public String getOnDuty(@PathVariable String id) {
        return reserveService.getOnDuty(id);
    }

    @ApiOperation(value = "获取预约时间")
    @PostMapping("/getAppointmentTime")
    public String getAppointmentTime(@RequestBody @Valid FindAppointmentTime appointment) {
        return reserveService.getAppointmentTime(appointment);
    }

    @ApiOperation(value = "重新预约患者")
    @PostMapping("/confirmAppointment")
    public String confirmAppointment(@RequestBody @Valid ConfirmAppointmentByDoctor confirmAppointment) {
        return reserveService.confirmAppointment(confirmAppointment);
    }

    @ApiOperation(value = "获取全部预约")
    @PostMapping("/currentAppointment")
    public String findCurrentAppointments(@RequestBody @Valid FindReserveByDoctor reserve) {
        return reserveService.findCurrentAppointments(reserve);
    }

    @ApiOperation(value = "提醒患者预约")
    @PostMapping("/remindPatientNumber")
    public String remindPatientNumber(@RequestBody @Valid RemindPatientByDoctor remindPatient) {
        return reserveService.remindPatientNumber(remindPatient);
    }

    @ApiOperation(value = "撤销预约")
    @PostMapping("/cancelAppointment")
    public String cancelAppointment(@RequestBody @Valid DeleteIdsBYAdmin ids) {
        return reserveService.cancelAppointment(ids);
    }

    @ApiOperation(value = "远程搜索")
    @GetMapping("/searchMedicinesRemotely/{drugName}")
    public String searchMedicinesRemotely(@PathVariable String drugName) {
        return reserveService.searchMedicinesRemotely(drugName);
    }

    @ApiOperation(value = "查找药品")
    @PostMapping("/findDrugByDrugName")
    public String findDrugByDrugName(@RequestBody OrderFindDrugListByDoctor drugName) {
        return reserveService.findDrugByDrugName(drugName);
    }

    @ApiOperation(value = "为患者配药")
    @PostMapping("/addDrugByPatient")
    public String addDrugByPatient(@RequestBody @Valid AddDrugByPatient addDrug) {
        return reserveService.addDrugByPatient(addDrug);
    }

    @ApiOperation(value = "删除药品")
    @PostMapping("/removeDrug")
    public String removeDrug(@RequestBody @Valid RemoveDrugByDoctor drug) {
        return reserveService.removeDrug(drug);
    }

    @ApiOperation(value = "结束预约")
    @GetMapping("/endAppointment/{orderId}")
    public String endAppointment(@PathVariable String orderId) {
        return reserveService.endAppointment(orderId);
    }

    @ApiOperation(value = "查找发药界面")
    @PostMapping("/findDispensingInterface")
    public String findDispensingInterface(@RequestBody FindDispensingInterfaceByDoctor findDispensing){
        return reserveService.findDispensingInterface(findDispensing);
    }

    @ApiOperation(value = "发药")
    @PostMapping("/dispensingMedicine")
    public String dispensingMedicine(@RequestBody DispensingMedicineByDoctor checkOrder){
        return reserveService.dispensingMedicine(checkOrder);
    }

    @ApiOperation(value = "定时检测时候存在超时患者")
    @Scheduled(cron = "0 0/30 * * * ?")
    public void testingPatientsAppointments(){
        reserveService.testingPatientsAppointments();
    }
}
