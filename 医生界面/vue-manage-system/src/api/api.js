import axios from 'axios';
let bases='http://localhost:8000/clinic';
export const findSexCoding = (config) => { return axios.get(`${bases}/general/findSex`,config); };
export const loginDoctor = (params,config) => { return axios.post(`${bases}/loginDoctor/login`,params,config); };
export const findHistoryAppointment = (params,config)  => { return axios.post(`${bases}/orderByDoctor/historyAppointment`,params,config); };
export const findAllByAllDuty = (config) => { return axios.get(`${bases}/general/findAllByAllDuty`,config); };
export const findDepartmentCoding = (config) => { return axios.get(`${bases}/department/findDepartmentCoding`,config); };
export const findDutyTimeCoding = (config) => { return axios.get(`${bases}/general/findDutyTimeCoding`,config); };
export const findPayInformation = (config) => { return axios.get(`${bases}/general/findPayInformation`,config); };
export const findDrugSpecification = (config) => { return axios.get(`${bases}/general/findDrugSpecification`,config); };
export const checkOrderInformation = (params,config)  => { return axios.post(`${bases}/orderByDoctor/checkOrder`,params,config); };
export const checkOrderDrug = (params,config)  => { return axios.get(`${bases}/orderByDoctor/checkOrderDrug/`+params,config); };
export const getTotalPrice = (params,config)  => { return axios.get(`${bases}/order/getTotalPrice/` + params,config); };
export const getOnDutyHours = (params,config)  => { return axios.get(`${bases}/orderByDoctor/getOnDuty/` + params,config); };
export const getAppointmentTime = (params,config)  => { return axios.post(`${bases}/orderByDoctor/getAppointmentTime` , params,config); };
export const confirmAppointment = (params,config)  => { return axios.post(`${bases}/orderByDoctor/confirmAppointment` , params,config); };
export const findCurrentAppointment = (params,config)  => { return axios.post(`${bases}/orderByDoctor/currentAppointment`,params,config); };
export const remindPatientNumber = (params,config)  => { return axios.post(`${bases}/orderByDoctor/remindPatientNumber`,params,config); };
export const cancelAppointment = (params,config)  => { return axios.post(`${bases}/orderByDoctor/cancelAppointment`,params,config); };
export const searchMedicinesRemotely = (params,config)  => { return axios.get(`${bases}/orderByDoctor/searchMedicinesRemotely/`+params,config); };
export const findDrugByDrugName = (params,config)  => { return axios.post(`${bases}/orderByDoctor/findDrugByDrugName`,params,config); };
export const addDrugByPatient = (params,config)  => { return axios.post(`${bases}/orderByDoctor/addDrugByPatient`,params,config); };
export const removeDrug = (params,config)  => { return axios.post(`${bases}/orderByDoctor/removeDrug`,params,config); };
export const endAppointmentByDoctor = (params,config)  => { return axios.get(`${bases}/orderByDoctor/endAppointment/`+params,config); };
export const findDispensingInterface = (params,config)  => { return axios.post(`${bases}/orderByDoctor/findDispensingInterface`,params,config); };
export const dispensingMedicine = (params,config)  => { return axios.post(`${bases}/orderByDoctor/dispensingMedicine`,params,config); };
export const editDoctorInformation = (params,config)  => { return axios.post(`${bases}/doctorInformationByDoctor/editDoctorInformation`,params,config); };
export const editDoctorLogin = (params,config)  => { return axios.post(`${bases}/doctorInformationByDoctor/editDoctorLogin`,params,config); };
export const getAppointmentNumber = (params,config)  => { return axios.get(`${bases}/doctorHome/getAppointmentNumber/`+params,config); };
export const findCurrentAppointmentMedicalTechnology = (config)  => { return axios.get(`${bases}/medicalTechnology/findCurrentAppointmentMedicalTechnology`,config); };
export const getAppointmentNumberMedicalTechnology = (config)  => { return axios.get(`${bases}/medicalTechnology/getAppointmentNumber`,config); };
export const findAllReserveMedicalTechnology = (params,config)  => { return axios.post(`${bases}/medicalTechnology/findAllReserveMedicalTechnology`,params,config); };
export const judgmentStatusPaid = (params,config)  => { return axios.get(`${bases}/medicalTechnology/judgmentStatusPaid/`+params,config); };
export const findDiagnoseReportData = (params,config)  => { return axios.get(`${bases}/medicalTechnology/findDiagnoseReportData/`+params,config); };
export const medicalTechnologyCancelAppointment = (params,config)  => { return axios.get(`${bases}/medicalTechnology/cancelAppointment/`+params,config); };
export const findHistoricalAppointments = (params,config)  => { return axios.post(`${bases}/medicalTechnology/findHistoricalAppointments`,params,config); };
export const viewReport = (params,config)  => { return axios.get(`${bases}/medicalTechnology/viewReport/`+params,config); };
export const endOfVisit = (params,config)  => { return axios.post(`${bases}/medicalTechnology/endOfVisit`,params,config); };
export const findBindOrderIdMedicalList = (params,config)  => { return axios.post(`${bases}/attendingPhysicianViewMedicalTechnology/findBindOrderIdMedicalList`,params,config); };
export const findHistoryBingOrderIdMedicalList = (params,config)  => { return axios.get(`${bases}/attendingPhysicianViewMedicalTechnology/findHistoryBingOrderIdMedicalList/`+params,config); };
