import request from '@/utils/httpRequest'

export default class TestValidationService {
  save (inputForm) {
    return request({
      url: '/test/validation/testValidation/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/validation/testValidation/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/validation/testValidation/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/validation/testValidation/list',
      method: 'get',
      params: params
    })
  }
}
