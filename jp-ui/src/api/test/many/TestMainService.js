import request from '@/utils/httpRequest'

export default class TestMainService {
  save (inputForm) {
    return request({
      url: '/test/many/testMain/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/many/testMain/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/many/testMain/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/many/testMain/list',
      method: 'get',
      params: params
    })
  }
}
