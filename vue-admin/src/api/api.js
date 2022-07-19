import axios from 'axios' //----------------------------注意这个地方--axios
var xhr = new XMLHttpRequest();
xhr.withCredentials = true;
let base = 'http://localhost:8080';
// let base = 'http://192.168.1.104:8080';

export const findByName = params => {
    return axios.get(`${base}/patient/findByNamePatient`, {params: params})
};

export const getUserList = params => {
    return axios.get(`${base}/user/list`, {params: params});
};

export const getUserListPage = params => {
    return axios.get(`${base}/patient/findAllPatient`, {params: params});
};

export const removeUser = params => {
    return axios.get(`${base}/patient/removePatientById`, {params: params});
};

export const batchRemovePatient = params => {
    return axios.get(`${base}/patient/batchRemovePatient`, {params: params});
};


export const addPatient = params => {
    return axios.post(`${base}/patient/addPatient`, params);
};
export const editPatient = params => {
    return axios.post(`${base}/patient/editPatient`, params);
};
export const exportData = params => {
    return axios.post(`${base}/patient/exportData`, "", {responseType: 'blob'});
};
export const templateDownload = params => {
    return axios.post(`${base}/patient/dataTemplateDownload`, "", {responseType: 'blob'});
};
export const analyseFile = params => {
    return axios.get(`${base}/patient/analyseFile`);
};

//doctor
export const DoctorFindByName = params => {
    return axios.get(`${base}/doctor/findByNameDoctor`, {params: params})
};

export const getDoctorListPage = params => {
    return axios.get(`${base}/doctor/findAllDoctor`, {params: params});
};

export const removeDoctor = params => {
    return axios.get(`${base}/doctor/removeDoctorById`, {params: params});
};

export const batchRemoveDoctor = params => {
    return axios.get(`${base}/doctor/batchRemoveDoctor`, {params: params});
};

export const addDoctor = params => {
    return axios.post(`${base}/doctor/addDoctor`, params);
};
export const editDoctor = params => {
    return axios.post(`${base}/doctor/editDoctor`, params);
};
export const exportDataDoctor = params => {
    return axios.post(`${base}/doctor/exportData`, "", {responseType: 'blob'});
};
export const templateDownloadDoctor = params => {
    return axios.post(`${base}/doctor/dataTemplateDownload`, "", {responseType: 'blob'});
};
export const analyseFileDoctor = params => {
    return axios.get(`${base}/doctor/analyseFile`);
};

//科室
export const findAllDepartment = params => {
    return axios.get(`${base}/department/findAllDepartment`);
};
export const findDepartmentDoctor = params => {
    return axios.post(`${base}/department/findDepartmentDoctor`, {params : params});
};

//诊所
export const drugFindByName = params => {
    return axios.get(`${base}/drug/findByNameDrug`, {params: params})
};

export const getDrugListPage = params => {
    return axios.get(`${base}/drug/findAllDrug`, {params: params});
};

export const addDrug = params => {
    return axios.post(`${base}/drug/addDrug`, params);
};
export const editDrug = params => {
    return axios.post(`${base}/drug/editDrug`, params);
};
export const exportDataDrug = params => {
    return axios.post(`${base}/drug/exportData`, "", {responseType: 'blob'});
};
export const templateDownloadDrug = params => {
    return axios.post(`${base}/drug/dataTemplateDownload`, "", {responseType: 'blob'});
};
export const analyseFileDrug = params => {
    return axios.get(`${base}/drug/analyseFile`);
};
export const exitDrugExhibit = params => {
    return axios.post(`${base}/drug/exitDrugExhibit`,params);
};

//预约时间界面所需连接
export const getMorningTime = params => {
    return axios.get(`${base}/appointment/findTimeMorning`, {params: params});
};
export const getAfternoon = params => {
    return axios.get(`${base}/appointment/findTimeAfternoon`, {params: params});
};
export const findSwitch = params => {
    return axios.get(`${base}/appointment/findSwitch`, {params: params});
};
export const exitNumber = params => {
    return axios.get(`${base}/appointment/exitNumber`, {params: params});
};

//预约信息
export const GetReservationTimeList = params => {
    return axios.get(`${base}/reservationInformation/findAllReservationInformation`, {params: params});
};
export const deleteReservation = params => {
    return axios.get(`${base}/reservationInformation/deleteById`, {params: params});
};
export const findByIdReservation = params => {
    return axios.get(`${base}/reservationInformation/findById`, {params: params});
};
export const findByIdRegistration = params => {
    return axios.get(`${base}/registrationInformation/findById`, {params: params});
};

//login
export const GetAllAds = params => {
    return axios.get(`${base}/page/AllAds`, {params: params});
};
export const getImgUrl = params => {
    return axios.get(`${base}/login/getImgUrl`, {params: params});
};
export const loginOut = params => {
    return axios.get(`${base}/login/loginOut`, {params: params});
};
export const adminRequestLogin = params => {
    return axios.post(`${base}/login/adminLogin`, params).then(res => res.data);
};
export const requestLogin = params => {
    return axios.post(`/login`, params).then(res => res.data);
};

export const findByNameReservation = params => {
    return axios.get(`${base}/reservationInformation/findByName`, {params: params});
};
export const DoctorFindByNameList = params => {
    return axios.get(`${base}/doctor/findByNameList`, {params: params});
};
export const exitAdminInformation = params => {
    return axios.post(`${base}/adminInformation/editAdminInformation`,params);
};export const editAdminLogin = params => {
    return axios.post(`${base}/adminInformation/editAdminLogin`,params);
};