import request from '@/utils/httpRequest'

export default class StudentCourseService {
  save (inputForm) {
    return request({
      url: '/test/manytomany/studentCourse/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/manytomany/studentCourse/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/manytomany/studentCourse/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/manytomany/studentCourse/list',
      method: 'get',
      params: params
    })
  }
}
