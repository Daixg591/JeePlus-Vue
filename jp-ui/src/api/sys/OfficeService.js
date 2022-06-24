import request from '@/utils/httpRequest'

export default class OfficeService {
  save (inputForm) {
    return request({
      url: `/sys/office/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/sys/office/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/office/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  treeData (params) {
    return request({
      url: '/sys/office/treeData',
      method: 'get',
      params: params
    })
  }
}
