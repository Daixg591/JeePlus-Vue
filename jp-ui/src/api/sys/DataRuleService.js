import request from '@/utils/httpRequest'

export default class DateRuleService {
  save (inputForm) {
    return request({
      url: `/sys/dataRule/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (id) {
    return request({
      url: '/sys/dataRule/delete',
      method: 'delete',
      params: {id: id}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/dataRule/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/sys/dataRule/list',
      method: 'get',
      params: params
    })
  }
  treeData () {
    return request({
      url: `/sys/dataRule/treeData`,
      method: 'get'
    })
  }
}
