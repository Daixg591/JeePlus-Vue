import Vue from 'vue'
import axios from 'axios'
// import axiosRetry from 'axios-retry'
import router from '@/router'
import { clearLoginInfo } from '@/utils'
import qs from 'qs'
import {
  Message,
  Loading
} from 'element-ui'

// 超时时间
axios.defaults.timeout = 100000
// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true
axios.defaults.headers = {'Content-Type': 'application/json; charset=utf-8'}
// axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8'
// 非生产环境 && 开启代理, 接口前缀统一使用[/api]前缀做代理拦截!
const BASE_URL = process.env.NODE_ENV !== 'production' ? process.env.VUE_APP_BASE_API : process.env.VUE_APP_SERVER_URL
// 对面暴露的基础请求路径
axios.BASE_URL = BASE_URL

/**
 * 请求拦截
 */
let loading
axios.interceptors.request.use(config => {
  let showLoading = false
  if (config.loading === true) {
    showLoading = true
  }
  if (showLoading) {
    loading = Loading.service({
      text: config.loadingText || 'Loading...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
  // 请求头带上token
  if (Vue.cookie.get('token')) {
    config.headers.token = Vue.cookie.get('token')
  }
  // 请求地址处理
  if (!config.url.startsWith('http')) {
    config.url = BASE_URL + config.url
  }

  const type = config.method
  const arrayFormat = config.headers.arrayFormat || 'indices'
  if (type === 'post' && config.headers['Content-Type'] === 'application/x-www-form-urlencoded; charset=utf-8') {
    // post请求参数处理
    config.data = qs.stringify(config.data, { allowDots: true, arrayFormat: arrayFormat })
  } else if (type === 'get') {
    // get请求参数处理
    config.paramsSerializer = (params) => {
      return qs.stringify(params, {
        allowDots: true, arrayFormat: arrayFormat
      })
    }
  }
  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 响应拦截
 */
axios.interceptors.response.use(response => {
  if (loading) {
    loading.close()
  }
  return response
}, error => {
  if (loading) {
    loading.close()
  }
  if (error.response.status === 408) { // 超时自动刷新
    axios({
      url: '/sys/refreshToken',
      method: 'get',
      params: { refreshToken: Vue.cookie.get('refreshToken') }
    }).then(({ data }) => {
      Vue.cookie.set('token', data.token)
      Vue.cookie.set('refreshToken', data.refreshToken)
    })
  } else if (
    error.response.status === 401) { // 需要重新登录
    clearLoginInfo()
    router.push({ name: 'login' })
    Message({
      message: error.response.data,
      type: 'error',
      showClose: true,
      dangerouslyUseHTMLString: true,
      duration: 3000
    })
  } else if (error.response.status === 404) { // 路径找不到
    Message({
      message: '404 路径找不到' + ': ' + error.response.config.url,
      type: 'error',
      showClose: true,
      duration: 3000
    })
  } else if (error.response.status === 503) {
    Message({
      message: '503 服务不可用' + ': ' + error.response.config.url,
      type: 'error',
      showClose: true,
      dangerouslyUseHTMLString: true,
      duration: 3000
    })
  } else if (error.response.status === 504) {
    Message({
      message: '504 网络连接错误' + ': ' + error.response.data,
      type: 'error',
      showClose: true,
      dangerouslyUseHTMLString: true,
      duration: 3000
    })
  } else {
    Message({
      message: error.response.data || error.response || error,
      type: 'error',
      showClose: true,
      dangerouslyUseHTMLString: true,
      duration: 5000
    })
  }

  return Promise.reject(error)
})

// 配置axios
// axiosRetry(axios, {
//   retries: 3,  // 设置自动发送请求次数
//   retryCondition: () => {
//       // true为打开自动发送请求，false为关闭自动发送请求
//       // 这里的意思是当请求方式为get时打开自动发送请求功能
//     return false
//   }
// })
export default axios
