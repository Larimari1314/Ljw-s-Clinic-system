import axios from 'axios';
let bases='http://localhost:8000/clinic';
/*基础信息*/
export const findSexCoding = (config) => { return axios.get(`${bases}/general/findSex`,config); };
export const findAllByAllDuty = (config) => { return axios.get(`${bases}/general/findAllByAllDuty`,config); };
export const findDepartmentCoding = (config) => { return axios.get(`${bases}/department/findDepartmentCoding`,config); };
export const findDutyTimeCoding = (config) => { return axios.get(`${bases}/general/findDutyTimeCoding`,config); };
export const findPayInformation = (config) => { return axios.get(`${bases}/general/findPayInformation`,config); };
export const findDrugSpecification = (config) => { return axios.get(`${bases}/general/findDrugSpecification`,config); };
/*doctor*/
export const findAllDoctor = (params,config) => { return axios.post(`${bases}/doctorInformation/findAllDoctor`, params,config); };
export const editDoctorInformation = (params,config) => { return axios.post(`${bases}/doctorInformation/editDoctorInformation`, params,config); };
export const addDoctorInformation = (params,config) => { return axios.post(`${bases}/doctorInformation/addDoctorInformation`, params,config); };
export const deleteByIdsInDoctor = (params,config) => { return axios.post(`${bases}/doctorInformation/deleteByIdsInDoctor`, params,config); };
export const dataTemplateDownloadByDoctorInformation = (params,config) => { return axios.post(`${bases}/doctorInformation/dataTemplateDownload`, params,config); };
export const exportDataByDoctorInformation = (params,config) => { return axios.post(`${bases}/doctorInformation/exportData`, params,config); };
export const analyseFileByDoctorInformation = (config) => { return axios.get(`${bases}/doctorInformation/analyseFile`,config)};
/*patient*/
export const findAllPatient = (params,config) => { return axios.post(`${bases}/patientInformation/findAllPatient`, params,config); };
export const editPatientInformation = (params,config) => { return axios.post(`${bases}/patientInformation/editPatientInformation`, params,config); };
export const addPatientInformation = (params,config) => { return axios.post(`${bases}/patientInformation/addPatientInformation`, params,config); };
export const deleteByIdsInPatient = (params,config) => { return axios.post(`${bases}/patientInformation/deleteByIdsInPatient`, params,config); };
export const dataTemplateDownloadByPatientInformation = (params,config) => { return axios.post(`${bases}/patientInformation/dataTemplateDownload`, params,config); };
export const exportDataByPatientInformation = (params,config) => { return axios.post(`${bases}/patientInformation/exportData`,params,config); };
export const analyseFileByPatientInformation = (config) => { return axios.get(`${bases}/patientInformation/analyseFile`,config); };
/*订单管理*/
export const findReserveNotViewed = (params,config) => { return axios.post(`${bases}/reserve/findReserveNotViewed`, params,config); };
export const cancelAppointment = (params,config) => { return axios.post(`${bases}/reserve/cancelAppointment`, params,config); };
export const cancelAppointmentViews = (params,config) => { return axios.post(`${bases}/reserve/cancelAppointmentViews`, params,config); };
export const resumeAppointment = (params,config) => { return axios.post(`${bases}/reserve/resumeAppointment`, params,config); };
/*全部订单*/
export const findAllOrderFrom = (params,config) => { return axios.post(`${bases}/order/findAllOrderFrom`, params,config); };
export const findOrderDrugList = (params,config) => { return axios.get(`${bases}/order/findOrderDrugList/` + params,config); };
export const getTotalPrice = (params,config) => { return axios.get(`${bases}/order/getTotalPrice/` + params,config); };
export const invoiceService = (params,config) => { return axios.get(`${bases}/order/invoiceService/` + params,config); };
/*基础信息维护*/
/*常规项管理*/
export const getAllGeneral = (params,config) => { return axios.post(`${bases}/general/getAllGeneral`,params,config); };
export const editGeneral = (params,config) => { return axios.post(`${bases}/general/editGeneral`,params,config); };
/*药品管理*/
export const findAllDrug = (params,config) => { return axios.post(`${bases}/drug/findAllDrug`,params,config); };
export const drugSwitching = (params,config) => { return axios.post(`${bases}/drug/switching`,params,config); };
export const editDrug = (params,config) => { return axios.post(`${bases}/drug/editDrug`,params,config); };
export const addDrug = (params,config) => { return axios.post(`${bases}/drug/addDrug`,params,config); };
export const deleteDrugByIds = (params,config) => { return axios.post(`${bases}/drug/deleteDrugByIds`,params,config); };
export const dataTemplateDownloadByDrug = (params,config) => { return axios.post(`${bases}/drug/dataTemplateDownload`, params,config); };
export const exportDataByDrug = (params,config) => { return axios.post(`${bases}/drug/exportData`,params,config); };
export const analyseFileByDrug = (config) => { return axios.get(`${bases}/drug/analyseFile`,config); };
/*页面广告管理*/
export const findAllLandingAds = (params,config) => { return axios.post(`${bases}/LandingAds/findAllLandingAds`,params,config); };
export const editLandingAds = (params,config) => { return axios.post(`${bases}/LandingAds/editLandingAds`,params,config); };
export const deleteLandingAds = (params,config) => { return axios.post(`${bases}/LandingAds/deleteLandingAds`,params,config); };
export const addLandingAds = (params,config) => { return axios.post(`${bases}/LandingAds/addLandingAds`,params,config); };
/*预约管理*/
export const findAllDoctorDuty = (params,config) => { return axios.post(`${bases}/doctorDuty/findAllDoctorDuty`,params,config); };
export const editDoctorDuty = (params,config) => { return axios.post(`${bases}/doctorDuty/editDoctorDuty`,params,config); };
export const findDutyTime = (params,config) => { return axios.post(`${bases}/doctorDuty/findDutyTime`,params,config); };
export const editUniteDoctorDutyTime = (params,config) => { return axios.post(`${bases}/doctorDuty/editUniteDoctorDutyTime`,params,config); };
/*挂号级别管理*/
export const findAllRegistrationLevel = (params,config) => { return axios.post(`${bases}/registrationLevel/findAllRegistrationLevel`,params,config); };
export const editRegistrationLevel = (params,config) => { return axios.post(`${bases}/registrationLevel/editRegistrationLevel`,params,config); };
/*科室管理*/
export const findAllDepartment = (params,config) => { return axios.post(`${bases}/department/findAllDepartment`,params,config); };
export const editDepartment = (params,config) => { return axios.post(`${bases}/department/editDepartment`,params,config); };
export const addDepartment = (params,config) => { return axios.post(`${bases}/department/addDepartment`,params,config); };
/*主页设置*/
export const mainGetNumber = (config) => { return axios.get(`${bases}/adminHome/mainGetNumber`,config); };
export const getAllValues = (config) => { return axios.get(`${bases}/adminHome/getValueNumber`,config); };
export const getDayNumber = (config) => { return axios.get(`${bases}/adminHome/getDayNumber`,config); };
export const getStateStatistics = (config) => { return axios.get(`${bases}/adminHome/getStateStatistics`,config); };
export const getRegisDoctor = (config) => { return axios.get(`${bases}/adminHome/getRegisDoctor`,config); };
/*登陆界面*/
export const GetAllAds = (config) => { return axios.get(`${bases}/loginAdmin/GetAllAds`,config); };
export const getAdminImgUrl = (params,config) => { return axios.get(`${bases}/loginAdmin/getAdminImgUrl/`+params,config); };
export const adminRequestLogin = (params,config) => { return axios.post(`${bases}/loginAdmin/login`,params,config); };
export const logoutAdmin = (params,config) => { return axios.post(`${bases}/loginAdmin/logout`,params,config); };
/*修改管理员信息*/
export const editAdminInformation = (params,config) => { return axios.post(`${bases}/loginAdmin/editAdminInformation`,params,config); };
export const editAdminLogin = (params,config) => { return axios.post(`${bases}/loginAdmin/editAdminLogin`,params,config); };
/*数据恢复区*/
export const checkDeleteDoctor = (params,config) => { return axios.post(`${bases}/doctorInformation/getDeleteData`,params,config); };
export const deletePermanentlyDoctor = (params,config) => { return axios.get(`${bases}/doctorInformation/deleteById/`+params,config); };
export const dataRecoveryDoctor = (params,config) => { return axios.get(`${bases}/doctorInformation/recoveryData/`+params,config); };
export const getDeleteDrug = (params,config) => { return axios.post(`${bases}/drug/getDeleteData`,params,config); };
export const deletePermanentlyDrug = (params,config) => { return axios.get(`${bases}/drug/deleteById/`+params,config); };
export const dataRecoveryDrug = (params,config) => { return axios.get(`${bases}/drug/recoveryData/`+params,config); };
export const getDeleteUser = (params,config) => { return axios.post(`${bases}/patientInformation/getDeleteData`,params,config); };
export const deletePermanentlyUser = (params,config) => { return axios.get(`${bases}/patientInformation/deleteById/`+params,config); };
export const dataRecoveryUser = (params,config) => { return axios.get(`${bases}/patientInformation/recoveryData/`+params,config); };

/*医技表数据*/
export const findAllMedicalTechnology = (params,config) => { return axios.post(`${bases}/medicalTechnologyByAdmin/findAllMedicalTechnology`,params,config); };
export const getMedicalProjectNameList = (config) => { return axios.get(`${bases}/medicalTechnologyByPatient/getMedicalProjectNameList`,config); };
export const findAllMedicalDoctor = (config) => { return axios.get(`${bases}/medicalTechnologyByAdmin/findAllMedicalDoctor`,config); };
export const editMedicalTechnology = (params,config) => { return axios.post(`${bases}/medicalTechnologyByAdmin/editMedicalTechnology`,params,config); };
export const findAllMedicalClassification = (params,config) => { return axios.post(`${bases}/medicalTechnologyByAdmin/findAllMedicalClassification`,params,config); };
export const editMedicalClassification = (params,config) => { return axios.post(`${bases}/medicalTechnologyByAdmin/editMedicalClassification`,params,config); };
export const findMedicalTechnologyOrder = (params,config) => { return axios.post(`${bases}/medicalTechnologyByAdmin/findMedicalTechnologyOrder`,params,config); };
export const viewTheReport = (params,config) => { return axios.get(`${bases}/medicalTechnologyOrderByPatient/viewTheReport/` + params,config); };
