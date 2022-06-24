import request from '@/utils/httpRequest'

export default class StudentService {
  save (inputForm) {
    return request({
      url: '/test/manytomany/student/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/manytomany/student/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/manytomany/student/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/manytomany/student/list',
      method: 'get',
      params: params
    })
  }
}
