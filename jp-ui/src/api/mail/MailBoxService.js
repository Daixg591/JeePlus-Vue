import request from '@/utils/httpRequest'

export default class MailBoxService {
  delete (ids) {
    return request({
      url: '/mail/box/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/mail/box/queryById`,
      method: 'get',
      params: {id: id}
    })
  }
  queryStatus () {
    return request({
      url: `/mail/box/queryStatus`,
      method: 'get'
    })
  }

  list (params) {
    return request({
      url: '/mail/box/list',
      method: 'get',
      params: params
    })
  }
}
