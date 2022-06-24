import request from '@/utils/httpRequest'

export default class CarService {
  save (inputForm) {
    return request({
      url: '/test/treetable/car/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/treetable/car/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/treetable/car/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/treetable/car/list',
      method: 'get',
      params: params
    })
  }
}
