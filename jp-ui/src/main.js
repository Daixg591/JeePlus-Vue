import Vue from 'vue'
import App from '@/App'
import router from '@/router'
import store from '@/store'
import VueCookie from 'vue-cookie'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/scss/index.scss'
import httpRequest from '@/utils/httpRequest'
import dictUtils from '@/utils/dictUtils'
import utils from '@/utils'
import '@/utils/filter'
import validator from '@/utils/validator'
import cloneDeep from 'lodash/cloneDeep'
import lodash from 'lodash/object'
import axios from 'axios'
import moment from 'moment'
import 'font-awesome/css/font-awesome.min.css'
// import i18n from './lang' // Internationalization
import VueClipboard from 'vue-clipboard2'
import VCharts from 'v-charts'
import JeeplusGencode from 'jeeplus-gencode'
import JeeplusFlow from 'jeeplus-flowable'
import 'jeeplus-flowable/lib/jeeplus-flowable.css'
import './directive'
import FormMaking from 'jeeplus-form/dist/JpFormMaking.common'
import 'jeeplus-form/dist/JpFormMaking.css'
import VueEditor from 'vue2-editor'
import Print from 'vue-print-nb'
import XEUtils from 'xe-utils'
// 按需导入 vxeTable
import {
        // 核心
        VXETable,

        // 功能模块
        Icon,
        Filter,
        Menu,
        Edit,
        Export,
        Keyboard,
        Validator,
        Header,
        Footer,

        // 可选组件
        Column,
        Colgroup,
        Grid,
        Toolbar,
        Pager,
        Checkbox,
        CheckboxGroup,
        Radio,
        RadioGroup,
        RadioButton,
        Input,
        Textarea,
        Button,
        Modal,
        Tooltip,
        Form,
        FormItem,
        FormGather,
        Select,
        Optgroup,
        Option,
        Switch,
        List,
        Pulldown,

        // 表格
        Table
      } from 'vxe-table'
import zhCN from 'vxe-table/lib/locale/lang/zh-CN'

      // 按需加载的方式默认是不带国际化的，自定义国际化需要自行解析占位符 '{0}'，例如：
VXETable.setup({
  i18n: (key, args) => XEUtils.toFormatString(XEUtils.get(zhCN, key), args)
})

      // 表格功能
Vue.use(Header)
      .use(Footer)
      .use(Icon)
      .use(Filter)
      .use(Edit)
      .use(Menu)
      .use(Export)
      .use(Keyboard)
      .use(Validator)

      // 可选组件
      .use(Column)
      .use(Colgroup)
      .use(Grid)
      .use(Toolbar)
      .use(Pager)
      .use(Checkbox)
      .use(CheckboxGroup)
      .use(Radio)
      .use(RadioGroup)
      .use(RadioButton)
      .use(Input)
      .use(Textarea)
      .use(Button)
      .use(Modal)
      .use(Tooltip)
      .use(Form)
      .use(FormItem)
      .use(FormGather)
      .use(Select)
      .use(Optgroup)
      .use(Option)
      .use(Switch)
      .use(List)
      .use(Pulldown)

      // 安装表格
      .use(Table)

Vue.use(Print)
Vue.use(VueEditor)
Vue.use(FormMaking)

Vue.use(JeeplusGencode)
Vue.use(JeeplusFlow)
VueClipboard.config.autoSetContainer = true
Vue.use(VCharts)
Vue.use(VueClipboard)
Vue.use(VueCookie)
Vue.use(ElementUI)
// Vue.use(ElementUI, {
//   i18n: (key, value) => i18n.t(key, value)
// })

Vue.config.productionTip = false
// 挂载全局
Vue.prototype.$http = httpRequest // ajax请求方法
Vue.prototype.hasPermission = utils.hasPermission // 权限方法
Vue.prototype.treeDataTranslate = utils.treeDataTranslate // 树形数据转换
Vue.prototype.$utils = utils
Vue.prototype.$window = window
Vue.prototype.$dictUtils = dictUtils
Vue.prototype.recover = utils.recover
Vue.prototype.recoverNotNull = utils.recoverNotNull
Vue.prototype.$axios = axios
Vue.prototype.validator = validator
Vue.prototype.lodash = lodash
Vue.prototype.moment = moment
Vue.prototype.deepClone = utils.deepClone
Vue.prototype.validatenull = utils.validatenull
Vue.prototype.$events = new Vue()

utils.printLogo()

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG = {}
window.SITE_CONFIG['storeState'] = cloneDeep(store.state)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
