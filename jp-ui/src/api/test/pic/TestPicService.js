import request from '@/utils/httpRequest'

export default class TestPicService {
  save (inputForm) {
    return request({
      url: '/test/pic/testPic/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/pic/testPic/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/pic/testPic/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/pic/testPic/list',
      method: 'get',
      params: params
    })
  }
}
