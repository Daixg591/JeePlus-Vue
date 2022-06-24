import request from '@/utils/httpRequest'

export default class TestNoteService {
  save (inputForm) {
    return request({
      url: '/test/note/testNote/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/note/testNote/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/note/testNote/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/note/testNote/list',
      method: 'get',
      params: params
    })
  }
}
