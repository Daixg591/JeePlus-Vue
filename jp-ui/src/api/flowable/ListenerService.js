import request from '@/utils/httpRequest'

export default class ListenerService {
  save (inputForm) {
    return request({
      url: '/extension/listener/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/listener/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/listener/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/extension/listener/list',
      method: 'get',
      params: params
    })
  }
}
