import request from '@/utils/httpRequest'

export default class TestOneService {
  save (inputForm) {
    return request({
      url: '/test/one/testOne/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/one/testOne/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/one/testOne/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list () {
    return request({
      url: '/test/one/testOne/list',
      method: 'get'
    })
  }
}
