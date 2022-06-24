import request from '@/utils/httpRequest'

export default class FormDefinitionService {
  save (inputForm) {
    return request({
      url: '/extension/formDefinition/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/formDefinition/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/formDefinition/queryById`,
      method: 'get',
      params: {id: id}
    })
  }
  queryByJsonId (jsonId) {
    return request({
      url: `/extension/formDefinition/queryByJsonId`,
      method: 'get',
      params: {jsonId: jsonId}
    })
  }

  list (params) {
    return request({
      url: '/extension/formDefinition/list',
      method: 'get',
      params: params
    })
  }
}
