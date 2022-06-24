import request from '@/utils/httpRequest'

export default class ButtonService {
  save (inputForm) {
    return request({
      url: '/extension/button/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/button/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/button/queryById`,
      method: 'get',
      params: {id: id}
    })
  }
  validateCodeNoExist (params) {
    return request({
      url: `/extension/button/validateCodeNoExist`,
      method: 'get',
      params: params
    })
  }

  validateNameNoExist (params) {
    return request({
      url: `/extension/button/validateNameNoExist`,
      method: 'get',
      params: params
    })
  }

  list (params) {
    return request({
      url: '/extension/button/list',
      method: 'get',
      params: params
    })
  }
}
