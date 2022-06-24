import request from '@/utils/httpRequest'

export default class TestStudentService {
  save (inputForm) {
    return request({
      url: '/test/manytomany/testStudent/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/manytomany/testStudent/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/manytomany/testStudent/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/manytomany/testStudent/list',
      method: 'get',
      params: params
    })
  }
}
