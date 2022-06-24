import request from '@/utils/httpRequest'

export default class TestContinentService {
  save (inputForm) {
    return request({
      url: '/test/grid/testContinent/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/grid/testContinent/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/grid/testContinent/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/grid/testContinent/list',
      method: 'get',
      params: params
    })
  }
}
