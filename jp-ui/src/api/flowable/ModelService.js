import request from '@/utils/httpRequest'

export default class ModelService {
  deploy (params) {
    return request({
      url: '/flowable/model/deploy',
      method: 'put',
      params: params
    })
  }
  updateCategory (params) {
    return request({
      url: '/flowable/model/updateCategory',
      method: 'put',
      params: params
    })
  }
  copy (id) {
    return request({
      url: '/flowable/model/copy',
      method: 'get',
      params: {id: id}
    })
  }

  getBpmnXml (id) {
    return request({
      url: '/flowable/model/getBpmnXml',
      method: 'get',
      params: {id: id}
    })
  }
  delete (ids) {
    return request({
      url: '/flowable/model/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  saveModel (modelId, data) {
    return request({
      url: `/flowable/model/saveModel/${modelId}`,
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
      data: data
    })
  }

  list (params) {
    return request({
      url: '/flowable/model/list',
      method: 'get',
      params: params
    })
  }
}
