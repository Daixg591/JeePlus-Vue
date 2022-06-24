import request from '@/utils/httpRequest'

export default class TestOne1Service {
  save (inputForm) {
    return request({
      url: '/test/many/testOne1/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/many/testOne1/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/many/testOne1/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/many/testOne1/list',
      method: 'get',
      params: params
    })
  }
}
