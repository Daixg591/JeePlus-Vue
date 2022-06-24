import request from '@/utils/httpRequest'

export default class ReportService {
  delete (id) {
    return request({
      url: '/reports/delete',
      method: 'delete',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/reports/list',
      method: 'get',
      params: params
    })
  }
}
