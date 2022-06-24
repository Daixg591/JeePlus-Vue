import request from '@/utils/httpRequest'

export default class TestTreeService {
  save (inputForm) {
    return request({
      url: '/test/tree/testTree/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/tree/testTree/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/tree/testTree/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/test/tree/testTree/treeData',
      method: 'get'
    })
  }
}
