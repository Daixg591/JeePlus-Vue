import request from '@/utils/httpRequest'

export default class DataSourceService {
  save (inputForm) {
    return request({
      url: '/database/datalink/dataSource/save',
      method: 'post',
      data: inputForm
    })
  }

  test (inputForm) {
    return request({
      url: `/database/datalink/dataSource/test`,
      method: 'post',
      data: inputForm
    })
  }

  checkEnName (oldEnName, enName) {
    return request({
      url: '/database/datalink/dataSource/checkEnName',
      method: 'get',
      params: {
        oldEnName: oldEnName,
        enName: enName
      }
    })
  }

  delete (ids) {
    return request({
      url: '/database/datalink/dataSource/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/database/datalink/dataSource/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/database/datalink/dataSource/list',
      method: 'get',
      params: params
    })
  }

  treeData () {
    return request({
      url: `/database/datalink/dataSource/treeData`,
      method: 'get'
    })
  }
}
