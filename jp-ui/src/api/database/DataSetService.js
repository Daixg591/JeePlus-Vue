import request from '@/utils/httpRequest'

export default class DataSetService {
  save (inputForm) {
    return request({
      url: `/database/datamodel/dataSet/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/database/datamodel/dataSet/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/database/datamodel/dataSet/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  getMeta (params) {
    return request({
      url: `/database/datamodel/dataSet/getMeta`,
      method: 'get',
      headers: {arrayFormat: 'brackets'},
      params: params
    })
  }

  exec (params) {
    return request({
      url: '/database/datamodel/dataSet/exec',
      method: 'get',
      headers: {arrayFormat: 'brackets'},
      params: params
    })
  }

  list (params) {
    return request({
      url: '/database/datamodel/dataSet/list',
      method: 'get',
      params: params
    })
  }
}
