import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router"
import store from "@/store";
// import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/css/index.css'

createApp(App).use(router).use(store).use(ElementPlus).mount('#app')
