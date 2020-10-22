import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI, {Message} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

//导入全局样式
import './assets/css/global.css'
import axios from 'axios'
//配置默认的请求路径
axios.defaults.baseURL = "http://localhost:9999/"
Vue.prototype.$http = axios

Vue.prototype.$message = Message

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
