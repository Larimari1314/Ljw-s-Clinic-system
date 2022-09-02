import axios from 'axios';
let bases='http://localhost:8000/clinic';
let basesIp='http://192.168.1.106:8000/clinic';
export const findSexCoding = params => { return axios.get(`${bases}/general/findSex`); };
export const loginPatient = params => { return axios.post(`${bases}/loginPatient/login`,params); };
export const findCurrentAppointment = params => { return axios.post(`${bases}/orderByDoctor/currentAppointment`,params); };
export const editDoctorInformation = params => { return axios.post(`${bases}/doctorInformationByDoctor/editDoctorInformation`,params); };
export const editDoctorLogin = params => { return axios.post(`${bases}/doctorInformationByDoctor/editDoctorLogin`,params); };
export const getAppointmentNumber = params => { return axios.get(`${bases}/doctorHome/getAppointmentNumber/`+params); };
export const findAllDepartment = params => { return axios.post(`${bases}/department/findAllDepartment`,params); };
export const getDoctorListByDepartment = params => { return axios.get(`${bases}/departmentByPatient/getDoctorListByDepartment/`+params); };
export const getAppointmentTime = params => { return axios.post(`${bases}/orderByDoctor/getAppointmentTime` , params); };
export const getOnDutyHours = params => { return axios.get(`${bases}/orderByDoctor/getOnDuty/` + params); };
export const confirmAppointment = params => { return axios.post(`${bases}/orderByDoctor/confirmAppointment` , params); };
export const getAppointmentInformation = params => { return axios.post(`${bases}/departmentByPatient/getAppointmentInformation` , params); };
export const getPayAppointmentInformation = params => { return axios.get(`${bases}/departmentByPatient/getPayAppointmentInformation/` + params); };
export const getQRCode = params => { return axios.get(`${bases}/departmentByPatient/getQRCode/` + params); };
export const paymentStatus = params => { return axios.get(`${bases}/departmentByPatient/paymentStatus/` + params); };
export const payAppointment = params => { return axios.get(`${basesIp}/departmentByPatient/payAppointment/` + params); };
export const scanPayStatus = params => { return axios.get(`${basesIp}/departmentByPatient/scanPayStatus/` + params); };
export const findAllHistoryOrderForm = params => { return axios.post(`${bases}/orderByPatient/findAllHistoryOrderForm` , params); };
export const findOrderCompletion = params => { return axios.get(`${bases}/orderByPatient/findOrderCompletion/` + params); };
export const findUnpaidOrderForm = params => { return axios.post(`${bases}/orderByPatient/findUnpaidOrderForm` , params); };
export const getOrderInformation = params => { return axios.get(`${bases}/orderByPatient/getOrderInformation/` + params); };
export const payOrderBounding = params => { return axios.get(`${bases}/orderByPatient/payOrderBounding/` + params); };
export const getOrderInformationPaying = params => { return axios.get(`${bases}/orderByPatient/getOrderInformationPaying/` + params); };
export const getQRCodeOrder = params => { return axios.get(`${bases}/orderByPatient/getQRCodeOrder/` + params); };
export const scanPayStatusOrder = params => { return axios.get(`${basesIp}/orderByPatient/scanPayStatusOrder/` + params); };
export const payingOrder = params => { return axios.get(`${basesIp}/orderByPatient/payingOrder/` + params); };
export const refundOperationOrder = params => { return axios.get(`${bases}/orderByPatient/refundOperationOrder/` + params); };
export const getAllCurrentAppointment = params => { return axios.post(`${bases}/currentAppointmentByPatient/getAllCurrentAppointment` , params); };
export const cancelAppointment = params => { return axios.get(`${bases}/currentAppointmentByPatient/cancelAppointment/` + params); };
export const editPatientLogin = params => { return axios.post(`${bases}/patientInformationByPatient/editPatientLogin` , params); };
export const editPatientInformation = params => { return axios.post(`${bases}/patientInformationByPatient/editPatientInformation` , params); };
export const getRemindersNumber = params => { return axios.get(`${bases}/orderByPatient/getRemindersNumber/` + params); };
