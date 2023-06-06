import { createRouter, createWebHashHistory } from 'vue-router'

// import HelloWorld  from '../components/HelloWorld.vue'
import WebLogin from "@/views/WebLogin.vue";
import WebIndex from "@/views/WebIndex.vue";
import WebRegister from "@/views/WebRegister.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: WebIndex
  },
  {
    path:'/login',
    name: 'login',
    component: WebLogin
  },{
    path:'/index',
    name: 'index',
    component: WebIndex
  },{
    path:'/register',
    name: 'register',
    component: WebRegister
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router


