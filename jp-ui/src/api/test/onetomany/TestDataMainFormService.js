import request from '@/utils/httpRequest'

export default class TestDataMainFormService {
  save (inputForm) {
    return request({
      url: '/test/onetomany/testDataMainForm/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/onetomany/testDataMainForm/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/onetomany/testDataMainForm/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/onetomany/testDataMainForm/list',
      method: 'get',
      params: params
    })
  }
}
