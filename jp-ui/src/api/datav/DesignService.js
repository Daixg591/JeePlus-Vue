import request from '@/utils/httpRequest'

export default class DesignService {
  getList (params) {
    return request({
      url: '/datav/dataScreenCategory/treeData',
      method: 'get',
      params: params
    })
  }

  delete (ids) {
    return request({
      url: '/datav/dataMap/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/datav/dataMap/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/datav/dataMap/list',
      method: 'get',
      params: params
    })
  }
}
