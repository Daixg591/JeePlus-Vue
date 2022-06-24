import request from '@/utils/httpRequest'

export default class MailTrashService {
  delete (ids) {
    return request({
      url: '/mail/trash/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/mail/trash/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/mail/trash/list',
      method: 'get',
      params: params
    })
  }
}
