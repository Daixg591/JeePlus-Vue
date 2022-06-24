import request from '@/utils/httpRequest'

export default class LogService {
  list (params) {
    return request({
      url: '/sys/log/list',
      method: 'get',
      params: params
    })
  }

  mine (params) {
    return request({
      url: '/sys/log/data/mine',
      method: 'get',
      params: params
    })
  }

  delete (ids) {
    return request({
      url: '/sys/log/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  empty () {
    return request({
      url: '/sys/log/empty',
      method: 'delete'
    })
  }
}
