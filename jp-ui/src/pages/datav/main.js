
import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import dataV from '@jiaminghi/data-view'
import VueCookie from 'vue-cookie'
import httpRequest from '@/utils/httpRequest'

import './styles/common.scss'
// import '@/mock/'
//注册自定义组件
import './components/'
//导入主题文件
import './theme/index.js'
import router from './router.js'

Vue.prototype.$http = httpRequest // ajax请求方法
Vue.config.productionTip = false
Vue.use(window.AVUE, {
  size: 'mini'
});
Vue.use(VueCookie)
Vue.use(ElementUI);
Vue.use(dataV)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
