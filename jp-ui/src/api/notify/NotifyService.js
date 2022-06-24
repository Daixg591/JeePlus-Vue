import request from '@/utils/httpRequest'

export default class NotifyService {
  save (inputForm) {
    return request({
      url: `/notify/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/notify/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/notify/queryById`,
      method: 'get',
      params: {id: id}
    })
  }
  query (params) {
    return request({
      url: `/notify/queryById`,
      method: 'get',
      params: params
    })
  }

  list (params) {
    return request({
      url: '/notify/list',
      method: 'get',
      params: params
    })
  }
}
