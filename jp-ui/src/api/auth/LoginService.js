import request from '@/utils/httpRequest'

export default class LoginService {
  getCode () {
    return request({
      url: '/sys/getCode',
      method: 'get',
      responseType: 'blob'
    })
  }
  login (username, password, code) {
    return request({
      url: '/sys/login',
      method: 'post',
      data: {
        'username': username,
        'password': password,
        'code': code
      }
    })
  }
  logout () {
    return request({
      url: '/sys/logout',
      method: 'get'
    })
  }
}
