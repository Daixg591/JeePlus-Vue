import request from '@/utils/httpRequest'

export default class CourseService {
  save (inputForm) {
    return request({
      url: '/test/manytomany/course/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/manytomany/course/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/manytomany/course/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/manytomany/course/list',
      method: 'get',
      params: params
    })
  }
}
