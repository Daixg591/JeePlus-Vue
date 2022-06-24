import request from '@/utils/httpRequest'

export default class TestTree2Service {
  save (inputForm) {
    return request({
      url: '/test/tree/testTree2/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/tree/testTree2/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/tree/testTree2/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/test/tree/testTree2/treeData',
      method: 'get'
    })
  }
}
