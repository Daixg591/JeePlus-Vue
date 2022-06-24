import request from '@/utils/httpRequest'

export default class CategoryService {
  save (inputForm) {
    return request({
      url: '/test/shop/category/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/shop/category/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/shop/category/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/test/shop/category/treeData',
      method: 'get'
    })
  }
}
