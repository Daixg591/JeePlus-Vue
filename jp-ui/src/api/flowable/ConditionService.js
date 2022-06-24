import request from '@/utils/httpRequest'

export default class ConditionService {
  save (inputForm) {
    return request({
      url: '/extension/condition/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/condition/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/condition/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/extension/condition/list',
      method: 'get',
      params: params
    })
  }
}
