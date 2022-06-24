import request from '@/utils/httpRequest'

export default class EchartsService {
  save (inputForm) {
    return request({
      url: `/echarts/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/echarts/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/echarts/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  mergeChartData (params) {
    return request({
      url: `/echarts/mergeChartData`,
      method: 'get',
      params: params
    })
  }

  queryDesignById (id) {
    return request({
      url: `/echarts/queryDesignById`,
      method: 'get',
      params: {id: id}
    })
  }

  getChartData (id) {
    return request({
      url: `/echarts/getChartData/${id}`,
      method: 'get'
    })
  }
  list (params) {
    return request({
      url: '/echarts/list',
      method: 'get',
      params: params
    })
  }
}
