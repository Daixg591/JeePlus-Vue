import request from '@/utils/httpRequest'

export default class GoodsService {
  save (inputForm) {
    return request({
      url: '/test/shop/goods/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/shop/goods/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/shop/goods/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/shop/goods/list',
      method: 'get',
      params: params
    })
  }
}
