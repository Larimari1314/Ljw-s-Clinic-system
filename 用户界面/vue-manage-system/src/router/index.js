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
                    title: '当前预约'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/appointment/currentAppointment.vue")
            },{
                path: "/appointmentDoctor",
                name: "appointmentDoctor",
                meta: {
                    title: '预约医生'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/appointment/appointmentDoctor.vue")
            },{
                path: "/historyOrder",
                name: "historyOrder",
                meta: {
                    title: '历史订单'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/order/historyOrder.vue")
            }, {
                path: "/unpaidOrder",
                name: "unpaidOrder",
                meta: {
                    title: '未支付订单'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/order/unpaidOrder.vue")
            },    {
                path: "/upload",
                name: "upload",
                meta: {
                    title: '上传插件'
                },
                component: () => import ( /* webpackChunkName: "upload" */ "../views/Upload.vue")
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
    },
    {
        path:  '/:catchAll(.*)',
        hidden: true,
        redirect: { path: '/404' }
    },{
        path: "/pay/:id",
        name: "pay",
        meta: {
            title: '支付'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/pay/pay.vue")
    },{
        path: "/paying/:id",
        name: "paying",
        meta: {
            title: '支付'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/pay/paying.vue")
    },{
        path: "/payOrder/:id",
        name: "payOrder",
        meta: {
            title: '支付'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/pay/payOrder.vue")
    },{
        path: "/payingOrder/:id",
        name: "payingOrder",
        meta: {
            title: '支付'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/pay/payingOrder.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = JSON.parse(localStorage.getItem("patient"));
    if (!role && to.path !== '/login' && to.path.slice(0,7)!=='/paying' && to.path.slice(0,12)!=='/payingOrder' ) {
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
