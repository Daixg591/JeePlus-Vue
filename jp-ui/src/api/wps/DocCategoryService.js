import request from '@/utils/httpRequest'

export default class DocCategoryService {
  save (inputForm) {
    return request({
      url: `/wps/docCategory/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/wps/docCategory/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/wps/docCategory/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/wps/docCategory/treeData',
      method: 'get'
    })
  }
}
