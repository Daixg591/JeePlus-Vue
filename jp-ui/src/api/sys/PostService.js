import request from '@/utils/httpRequest'

export default class PostService {
  save (inputForm) {
    return request({
      url: `/sys/post/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/sys/post/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/post/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/sys/post/list',
      method: 'get',
      params: params
    })
  }
}
