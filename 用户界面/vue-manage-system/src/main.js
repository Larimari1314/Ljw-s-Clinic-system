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
const xhr = new XMLHttpRequest();
xhr.withCredentials = true;
axios.defaults.baseURL ='http://localhost:8000/';
axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true;
