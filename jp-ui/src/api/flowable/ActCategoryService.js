import request from '@/utils/httpRequest'

export default class ActCategoryService {
  treeData () {
    return request({
      url: `/extension/actCategory/treeData`,
      method: 'get'
    })
  }

  save (inputForm) {
    return request({
      url: '/extension/actCategory/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/extension/actCategory/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/actCategory/queryById`,
      method: 'get',
      params: {id: id}
    })
  }
}
