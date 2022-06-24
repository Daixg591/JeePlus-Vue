import request from '@/utils/httpRequest'

export default class MakeFormService {
  save (inputForm) {
    return request({
      url: `/form/make/save`,
      method: 'post',
      data: inputForm
    })
  }

  saveFormSource (inputForm) {
    return request({
      url: `/form/make/saveFormSource`,
      method: 'post',
      data: inputForm
    })
  }

  saveBasicInfo (inputForm) {
    return request({
      url: `/form/make/saveBasicInfo`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/form/make/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/form/make/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  getTableColumnList (params) {
    return request({
      url: `/form/make/getTableColumnList`,
      method: 'get',
      params: params
    })
  }

  getTableList (params) {
    return request({
      url: `/form/make/getTableList`,
      method: 'get',
      params: params
    })
  }

  list (params) {
    return request({
      url: '/form/make/list',
      method: 'get',
      params: params
    })
  }

  validateTableNoExist (params) {
    return request({
      url: `/form/make/validateTableNoExist`,
      method: 'get',
      params: params
    })
  }

  validateKeyNoExist (params) {
    return request({
      url: `/form/make/validateKeyNoExist`,
      method: 'get',
      params: params
    })
  }

  createMenu (inputForm) {
    return request({
      url: `/form/make/createMenu`,
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
      data: inputForm
    })
  }
}
