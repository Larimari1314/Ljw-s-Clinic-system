import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import doctorInformation from "./views/personnelInformation/doctorInformation";
import patirntInformation from "./views/personnelInformation/patirntInformation";
import reserveNotViewed from "./views/order/reserveNotViewed";
import orderViewed from "./views/order/orderViewed";
import cancelAppointmentView from "./views/order/cancelAppointmentView";
import bill from "./views/order/bill";
import general from "./views/basicInformationMaintenance/general";
import landingAds from "./views/basicInformationMaintenance/landingAds";
import dutyTime from "./views/basicInformationMaintenance/dutyTime";
import registrationLevel from "./views/basicInformationMaintenance/registrationLevel";
import department from "./views/basicInformationMaintenance/department";
import drug from "./views/basicInformationMaintenance/drug";
import homeMain from "./views/home/homeMain";
import dataRecovery from "./views/basicInformationMaintenance/dataRecovery";
let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path:'/bill/:id',
        component:bill,
        name:'',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-home',
        leaf: true,//只有一个节点
        children: [
            { path: '/HomeMain', component: homeMain, name: '首页' }
        ]
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '人员信息管理',
        iconCls: 'fa fa-user-circle',//图标样式class
        children: [
            { path: '/doctor', component: doctorInformation, name: '医生信息管理' },
            { path: '/patient', component: patirntInformation, name: '患者信息管理' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '订单管理',
        iconCls: 'fa fa-credit-card-alt',
        children: [
            { path: '/reserveNotViewed', component: reserveNotViewed, name: '已预约信息查看' },
            { path: '/cancelAppointmentView', component: cancelAppointmentView, name: '撤销预约信息查看' },
            { path: '/orderViewed', component: orderViewed, name: '查看全部订单情况' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '医院基本信息维护',
        iconCls: 'fa fa-sitemap',
        children: [
            { path: '/general', component: general, name: '常规项管理' },
            { path: '/drug', component: drug, name: '药品管理' },
            { path: '/landingAds', component: landingAds, name: '页面广告管理' },
            { path: '/dutyTime', component: dutyTime, name: '预约时间管理' },
            { path: '/registrationLevel', component: registrationLevel, name: '挂号级别管理' },
            { path: '/department', component: department, name: '科室管理' },
            { path: '/dataRecovery', component: dataRecovery, name: '数据恢复' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;
