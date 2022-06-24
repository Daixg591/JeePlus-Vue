import request from '@/utils/httpRequest'

export default class NodeSettingService {
  save (inputForm) {
    return request({
      url: '/extension/nodeSetting/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/nodeSetting/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryValueByKey (params) {
    return request({
      url: `/extension/nodeSetting/queryValueByKey`,
      method: 'get',
      params: params
    })
  }

  queryById (id) {
    return request({
      url: `/extension/nodeSetting/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/extension/nodeSetting/list',
      method: 'get',
      params: params
    })
  }
}
