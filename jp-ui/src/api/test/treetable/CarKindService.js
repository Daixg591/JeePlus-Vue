import request from '@/utils/httpRequest'

export default class CarKindService {
  save (inputForm) {
    return request({
      url: '/test/treetable/carKind/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/treetable/carKind/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/treetable/carKind/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/test/treetable/carKind/treeData',
      method: 'get'
    })
  }
}
