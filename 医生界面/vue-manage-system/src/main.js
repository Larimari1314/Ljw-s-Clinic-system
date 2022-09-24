import {createApp} from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
const app = createApp(App)
installElementPlus(app)
app.use(createPinia())
    .use(router)
    .mount('#app')
import axios from 'axios'
import {ElMessage} from "element-plus";
const xhr = new XMLHttpRequest();
xhr.withCredentials = true;
axios.defaults.baseURL ='http://localhost:8000/';
axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true;
axios.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if(error.response.status === 403){
            window.location.href='http://localhost:3000/#/403'
            return Promise.reject(error);
        }if(error.response.status === 401){
            if(error.response.headers['token']==='false'){
                ElMessage.error('登录失效请重新登录.')
                window.location.href='http://localhost:3000/#/login'
                return Promise.reject(error);
            }else{
                ElMessage.error('登录状态异常，请稍后重新登录.')
                return Promise.reject(error);
            }
        }
    }
);
