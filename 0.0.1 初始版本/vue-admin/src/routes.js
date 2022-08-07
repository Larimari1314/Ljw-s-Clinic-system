// import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import PatientInformationManagement from './views/nav1/PatientInformationManagement.vue'
import RegistrationManagement from './views/nav1/RegistrationManagement.vue'
import DrugManagement from './views/nav3/DrugManagement.vue'
import DepartmentManagement from './views/nav2/DepartmentManagement.vue'
import echarts from './views/charts/echarts.vue'
import DoctorInformationManagement from "./views/nav2/DoctorInformationManagement";
import DepartmentDoctorList from "./views/nav2/DepartmentDoctorList.vue";
import NotFound from "./views/404.vue";
import permissionNotFound from "./views/error.vue";
import AppointmentManagement from "./views/nav3/AppointmentManagement";
import Login from './views/Login.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    }, {
        path: '/',
        component: Home,
        name: '',
        hidden: true,
        children: [
            {path: '/DepartmentDoctorList', component: DepartmentDoctorList}]

    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/permissionNotFound',
        component: permissionNotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '患者管理',
        iconCls: 'fa fa-users',//图标样式class
        children: [
            {path: '/main', component: Main, name: '主页', hidden: true},
            {path: '/PatientInformationManagement', component: PatientInformationManagement, name: '患者信息管理'},
            {path: '/RegistrationManagement', component: RegistrationManagement, name: '挂号管理'}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '诊所员工管理',
        iconCls: 'fa fa-user-md',
        children: [
            {path: '/DoctorInformationManagement', component: DoctorInformationManagement, name: '医生信息管理'},
            {path: '/DepartmentManagement', component: DepartmentManagement, name: '科室管理'}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '诊所信息管理',
        iconCls: 'fa fa-hospital-o',
        children: [
            {path: '/DrugManagement', component: DrugManagement, name: '药品管理'},
            {path: '/AppointmentManagement', component: AppointmentManagement, name: '医生预约管理'}
        ]
    },
  /*  {
        path: '/',
        component: Home,
        name: 'Charts',
        iconCls: 'fa fa-bar-chart',
        children: [
            {path: '/echarts', component: echarts, name: 'echarts'}
        ]
    },*/
    {
        path: '*',
        hidden: true,
        redirect: {path: '/404'}
    }

];

export default routes;