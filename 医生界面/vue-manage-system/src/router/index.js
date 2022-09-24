import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/login'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/setPersonalInformation",
                name: "setPersonalInformation",
                meta: {
                    title: '设置个人信息'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/base/setPersonalInformation.vue")
            },   {
                path: "/currentAppointment",
                name: "currentAppointment",
                meta: {
                    title: '当前预约患者'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/patientAppointment/currentAppointment.vue")
            }, {
                path: "/medicalTechnologyCurrentAppointment",
                name: "medicalTechnologyCurrentAppointment",
                meta: {
                    title: '医技预约患者'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/medicalTechnology/medicalTechnologyCurrentAppointment.vue")
            },{
                path: "/medicalTechnologyHistoryAppointment",
                name: "medicalTechnologyHistoryAppointment",
                meta: {
                    title: '医技历史预约'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/medicalTechnology/medicalTechnologyHistoryAppointment.vue")
            },{
                path: "/dspensingMedicine",
                name: "dspensingMedicine",
                meta: {
                    title: '患者发药'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/patientAppointment/dspensingMedicine.vue")
            },{
                path: "/historyAppointment",
                name: "historyAppointment",
                meta: {
                    title: '历史预约'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/patientAppointment/historyAppointment.vue")
            },  {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Dashboard.vue")
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import (/* webpackChunkName: "403" */ '../views/errorInterface/403.vue')
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/User.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Login.vue")
    }  , {
        path: '/404',
        name: '404',
        meta: {
            title: '找不到页面'
        },
        component: () => import (/* webpackChunkName: "404" */ '../views/errorInterface/404.vue'),
        hidden: true
    } , {
        path: '/diagnoseReport/:id',
        name: 'diagnoseReport',
        meta: {
            title: '为患者编写诊断报告'
        },
        component: () => import (/* webpackChunkName: "404" */ '../views/medicalTechnology/diagnoseReport.vue'),
        hidden: true
    },{
        path: '/viewTestReport/:id',
        name: 'viewTestReport',
        meta: {
            title: '查看诊断报告'
        },
        component: () => import (/* webpackChunkName: "404" */ '../views/medicalTechnology/viewTestReport.vue'),
        hidden: true
    },
    {
        path:  '/:catchAll(.*)',
        hidden: true,
        redirect: { path: '/404' }
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = JSON.parse(localStorage.getItem("doctor"));
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;
