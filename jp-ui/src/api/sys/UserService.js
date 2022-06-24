import request from '@/utils/httpRequest'

export default class UserService {
  save (inputForm) {
    return request({
      url: `/sys/user/save`,
      method: 'post',
      headers: {arrayFormat: 'repeat'},
      data: inputForm
    })
  }

  saveInfo (inputForm) {
    return request({
      url: `/sys/user/saveInfo`,
      method: 'post',
      headers: {arrayFormat: 'repeat'},
      data: inputForm
    })
  }

  savePwd (inputForm) {
    return request({
      url: `/sys/user/savePwd`,
      method: 'post',
      headers: {arrayFormat: 'repeat'},
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/sys/user/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/user/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/sys/user/list',
      method: 'get',
      params: params
    })
  }
}
