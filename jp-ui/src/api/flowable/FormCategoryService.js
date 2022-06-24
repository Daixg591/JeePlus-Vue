import request from '@/utils/httpRequest'

export default class FormCategoryService {
  save (inputForm) {
    return request({
      url: '/extension/formCategory/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (id) {
    return request({
      url: '/extension/formCategory/delete',
      method: 'delete',
      params: {id: id}
    })
  }

  queryById (id) {
    return request({
      url: `/extension/formCategory/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  treeData () {
    return request({
      url: '/extension/formCategory/treeData',
      method: 'get'
    })
  }
}
