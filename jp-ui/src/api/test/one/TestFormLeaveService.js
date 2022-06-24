import request from '@/utils/httpRequest'

export default class TestFormLeaveService {
  save (inputForm) {
    return request({
      url: '/test/one/testFormLeave/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/one/testFormLeave/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/one/testFormLeave/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/one/testFormLeave/list',
      method: 'get',
      params: params
    })
  }
}
