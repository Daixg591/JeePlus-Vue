import request from '@/utils/httpRequest'

export default class TestCountryService {
  save (inputForm) {
    return request({
      url: '/test/grid/testCountry/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/grid/testCountry/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/grid/testCountry/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/grid/testCountry/list',
      method: 'get',
      params: params
    })
  }
}
