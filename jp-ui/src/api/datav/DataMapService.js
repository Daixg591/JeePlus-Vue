import request from '@/utils/httpRequest'

export default class DataMapService {
  save (inputForm) {
    return request({
      url: `/datav/dataMap/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/datav/dataMap/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/datav/dataMap/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/datav/dataMap/list',
      method: 'get',
      params: params
    })
  }
}
