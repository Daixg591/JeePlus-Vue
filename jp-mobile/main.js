import Vue from 'vue'
import App from './App'

import store from './store'
import request from './common/request.js'
import dictUtils from '@/common/dictUtils.js'
import * as auth from "@/common/auth.js"
import * as utils from "@/common/util.js"
import cuCustom from './colorui/components/cu-custom.vue'
import BASE_URL from '@/common/config.js'
import '@/common/filter'
Vue.component('cu-custom',cuCustom)
Vue.config.productionTip = false
Vue.prototype.$store = store
Vue.prototype.$http = request
Vue.prototype.$auth = auth
Vue.prototype.$dictUtils = dictUtils
Vue.prototype.recover = utils.recover
Vue.prototype.BASE_URL = BASE_URL

// 注册全局组件
import MescrollBody from "@/components/mescroll-uni/mescroll-body.vue"
import MescrollUni from "@/components/mescroll-uni/mescroll-uni.vue"
import LyTree from '@/components/ly-tree/ly-tree.vue'
Vue.component('ly-tree', LyTree)
Vue.component('mescroll-body', MescrollBody)
Vue.component('mescroll-uni', MescrollUni)

import JeeplusFlow from 'jeeplus-flowable'
import 'jeeplus-flowable/lib/jeeplus-flowable.css'
Vue.use(JeeplusFlow)

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()

 



