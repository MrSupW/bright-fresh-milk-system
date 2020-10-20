import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login";
import Home from "../views/Home"
import Welcome from '../components/Welcome'
import MilkDrink from "../components/products/MilkDrink";
import Yogurt from "../components/products/Yogurt";
import Jelly from "../components/products/Jelly";
import PureMilk from "../components/products/PureMilk";
import S_MilkDrink from "../components/storages/MilkDrink";
import S_Yogurt from "../components/storages/Yogurt";
import S_Jelly from "../components/storages/Jelly";
import S_PureMilk from "../components/storages/PureMilk";
import HistoryOrders from "../components/order/HistoryOrders";
import CurrentOrders from "../components/order/CurrentOrders";
import CurrentOrderDetail from "../components/order/CurrentOrderDetail";
Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    redirect:'/login',
  },
  {
    path:'/login',
    name:'Login',
    component:Login,
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/welcome',
    children:[
      {
        path:'/welcome',
        name:'welcome',
        component:Welcome
      },
      {
        path: '/product/milkDrink',
        name: 'MilkDrink',
        component:MilkDrink,
      },
      {
        path: '/product/yogurt',
        name: 'Yogurt',
        component: Yogurt,
      },
      {
        path: '/product/jelly',
        name: 'Jelly',
        component: Jelly,
      },
      {
        path: '/product/pureMilk',
        name: 'PureMilk',
        component: PureMilk,
      },
      {
        path: '/storage/milkDrink',
        name: 'S_MilkDrink',
        component:S_MilkDrink,
      },
      {
        path: '/storage/yogurt',
        name: 'S_Yogurt',
        component: S_Yogurt,
      },
      {
        path: '/storage/jelly',
        name: 'S_Jelly',
        component: S_Jelly,
      },
      {
        path: '/storage/pureMilk',
        name: 'S_PureMilk',
        component: S_PureMilk,
      },
      {
        path: '/order/currentOrders',
        name: 'CurrentOrders',
        component: CurrentOrders
      },
      {
        path: '/order/historyOrders',
        name: 'HistoryOrders',
        component: HistoryOrders
      },
      {
        path: '/order/currentOrders/detail',
        name: 'CurrentOrderDetail',
        component:CurrentOrderDetail
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: '/bright-fresh-milk-system/',
  routes
})

router.beforeEach((to,from,next) =>{
  if(to.path === '/login') return next();
  if(!sessionStorage.getItem("token")) return next('/login');
  next()
})

export default router
