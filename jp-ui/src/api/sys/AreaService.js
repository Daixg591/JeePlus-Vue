import request from '@/utils/httpRequest'

export default class AreaService {
  save (inputForm) {
    return request({
      url: `/sys/area/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/sys/area/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/area/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/sys/area/treeData',
      method: 'get'
    })
  }
}
