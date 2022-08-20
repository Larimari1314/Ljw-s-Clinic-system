import axios from 'axios';
let bases='http://localhost:8000/clinic';
/*基础信息*/
export const findSexCoding = params => { return axios.get(`${bases}/general/findSex`); };
export const findAllByAllDuty = params => { return axios.get(`${bases}/general/findAllByAllDuty`); };
export const findDepartmentCoding = params => { return axios.get(`${bases}/department/findDepartmentCoding`); };
export const findDutyTimeCoding = params => { return axios.get(`${bases}/general/findDutyTimeCoding`); };
export const findPayInformation = params => { return axios.get(`${bases}/general/findPayInformation`); };
export const findDrugSpecification = params => { return axios.get(`${bases}/general/findDrugSpecification`); };
/*doctor*/
export const findAllDoctor = params => { return axios.post(`${bases}/doctorInformation/findAllDoctor`, params); };
export const editDoctorInformation = params => { return axios.post(`${bases}/doctorInformation/editDoctorInformation`, params); };
export const addDoctorInformation = params => { return axios.post(`${bases}/doctorInformation/addDoctorInformation`, params); };
export const deleteByIdsInDoctor = params => { return axios.post(`${bases}/doctorInformation/deleteByIdsInDoctor`, params); };
export const dataTemplateDownloadByDoctorInformation = params => { return axios.post(`${bases}/doctorInformation/dataTemplateDownload`, params,{responseType: 'blob'}); };
export const exportDataByDoctorInformation = params => { return axios.post(`${bases}/doctorInformation/exportData`, params,{responseType: 'blob'}); };
export const analyseFileByDoctorInformation = params => { return axios.get(`${bases}/doctorInformation/analyseFile`)};
/*patient*/
export const findAllPatient = params => { return axios.post(`${bases}/patientInformation/findAllPatient`, params); };
export const editPatientInformation = params => { return axios.post(`${bases}/patientInformation/editPatientInformation`, params); };
export const addPatientInformation = params => { return axios.post(`${bases}/patientInformation/addPatientInformation`, params); };
export const deleteByIdsInPatient = params => { return axios.post(`${bases}/patientInformation/deleteByIdsInPatient`, params); };
export const dataTemplateDownloadByPatientInformation = params => { return axios.post(`${bases}/patientInformation/dataTemplateDownload`, params,{responseType: 'blob'}); };
export const exportDataByPatientInformation = params => { return axios.post(`${bases}/patientInformation/exportData`,params,{responseType: 'blob'}); };
export const analyseFileByPatientInformation = params => { return axios.get(`${bases}/patientInformation/analyseFile`); };
/*订单管理*/
export const findReserveNotViewed = params => { return axios.post(`${bases}/reserve/findReserveNotViewed`, params); };
export const cancelAppointment = params => { return axios.post(`${bases}/reserve/cancelAppointment`, params); };
export const cancelAppointmentViews = params => { return axios.post(`${bases}/reserve/cancelAppointmentViews`, params); };
export const resumeAppointment = params => { return axios.post(`${bases}/reserve/resumeAppointment`, params); };
/*全部订单*/
export const findAllOrderFrom = params => { return axios.post(`${bases}/order/findAllOrderFrom`, params); };
export const findOrderDrugList = params => { return axios.get(`${bases}/order/findOrderDrugList/` + params); };
export const getTotalPrice = params => { return axios.get(`${bases}/order/getTotalPrice/` + params); };
export const invoiceService = params => { return axios.get(`${bases}/order/invoiceService/` + params); };
/*基础信息维护*/
/*常规项管理*/
export const getAllGeneral = params => { return axios.post(`${bases}/general/getAllGeneral`,params); };
export const editGeneral = params => { return axios.post(`${bases}/general/editGeneral`,params); };
/*药品管理*/
export const findAllDrug = params => { return axios.post(`${bases}/drug/findAllDrug`,params); };
export const drugSwitching = params => { return axios.post(`${bases}/drug/switching`,params); };
export const editDrug = params => { return axios.post(`${bases}/drug/editDrug`,params); };
export const addDrug = params => { return axios.post(`${bases}/drug/addDrug`,params); };
export const deleteDrugByIds = params => { return axios.post(`${bases}/drug/deleteDrugByIds`,params); };
export const dataTemplateDownloadByDrug = params => { return axios.post(`${bases}/drug/dataTemplateDownload`, params,{responseType: 'blob'}); };
export const exportDataByDrug = params => { return axios.post(`${bases}/drug/exportData`,params,{responseType: 'blob'}); };
export const analyseFileByDrug = params => { return axios.get(`${bases}/drug/analyseFile`); };
/*页面广告管理*/
export const findAllLandingAds = params => { return axios.post(`${bases}/LandingAds/findAllLandingAds`,params); };
export const editLandingAds = params => { return axios.post(`${bases}/LandingAds/editLandingAds`,params); };
export const deleteLandingAds = params => { return axios.post(`${bases}/LandingAds/deleteLandingAds`,params); };
export const addLandingAds = params => { return axios.post(`${bases}/LandingAds/addLandingAds`,params); };
/*预约管理*/
export const findAllDoctorDuty = params => { return axios.post(`${bases}/doctorDuty/findAllDoctorDuty`,params); };
export const editDoctorDuty = params => { return axios.post(`${bases}/doctorDuty/editDoctorDuty`,params); };
export const findDutyTime = params => { return axios.post(`${bases}/doctorDuty/findDutyTime`,params); };
export const editUniteDoctorDutyTime = params => { return axios.post(`${bases}/doctorDuty/editUniteDoctorDutyTime`,params); };
/*挂号级别管理*/
export const findAllRegistrationLevel = params => { return axios.post(`${bases}/registrationLevel/findAllRegistrationLevel`,params); };
export const editRegistrationLevel = params => { return axios.post(`${bases}/registrationLevel/editRegistrationLevel`,params); };
/*科室管理*/
export const findAllDepartment = params => { return axios.post(`${bases}/department/findAllDepartment`,params); };
export const editDepartment = params => { return axios.post(`${bases}/department/editDepartment`,params); };
export const addDepartment = params => { return axios.post(`${bases}/department/addDepartment`,params); };
/*主页设置*/
export const mainGetNumber = params => { return axios.get(`${bases}/adminHome/mainGetNumber`,params); };
export const getAllValues = params => { return axios.get(`${bases}/adminHome/getValueNumber`,params); };
export const getDayNumber = params => { return axios.get(`${bases}/adminHome/getDayNumber`,params); };
export const getStateStatistics = params => { return axios.get(`${bases}/adminHome/getStateStatistics`,params); };
export const getRegisDoctor = params => { return axios.get(`${bases}/adminHome/getRegisDoctor`,params); };
/*登陆界面*/
export const GetAllAds = params => { return axios.get(`${bases}/loginAdmin/GetAllAds`,params); };
export const getAdminImgUrl = params => { return axios.get(`${bases}/loginAdmin/getAdminImgUrl/`+params); };
export const adminRequestLogin = params => { return axios.post(`${bases}/loginAdmin/login`,params); };
export const logoutAdmin = params => { return axios.post(`${bases}/loginAdmin/logout`,params); };
/*修改管理员信息*/
export const editAdminInformation = params => { return axios.post(`${bases}/loginAdmin/editAdminInformation`,params); };
export const editAdminLogin = params => { return axios.post(`${bases}/loginAdmin/editAdminLogin`,params); };
/*数据恢复区*/
export const checkDeleteDoctor = params => { return axios.post(`${bases}/doctorInformation/getDeleteData`,params); };
export const deletePermanentlyDoctor = params => { return axios.get(`${bases}/doctorInformation/deleteById/`+params); };
export const dataRecoveryDoctor = params => { return axios.get(`${bases}/doctorInformation/recoveryData/`+params); };
export const getDeleteDrug = params => { return axios.post(`${bases}/drug/getDeleteData`,params); };
export const deletePermanentlyDrug = params => { return axios.get(`${bases}/drug/deleteById/`+params); };
export const dataRecoveryDrug = params => { return axios.get(`${bases}/drug/recoveryData/`+params); };
export const getDeleteUser = params => { return axios.post(`${bases}/patientInformation/getDeleteData`,params); };
export const deletePermanentlyUser = params => { return axios.get(`${bases}/patientInformation/deleteById/`+params); };
export const dataRecoveryUser = params => { return axios.get(`${bases}/patientInformation/recoveryData/`+params); };
