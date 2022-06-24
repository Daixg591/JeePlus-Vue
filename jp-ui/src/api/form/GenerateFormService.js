import request from '@/utils/httpRequest'

export default class GenerateFormService {
  save (inputForm) {
    return request({
      url: `/form/generate/save`,
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
      data: inputForm
    })
  }

  queryById (params) {
    return request({
      url: `/form/generate/queryById`,
      method: 'get',
      params: params
    })
  }

  delete (params) {
    return request({
      url: `/form/generate/delete`,
      method: 'delete',
      params: params
    })
  }

  list (params) {
    return request({
      url: '/form/generate/list',
      method: 'get',
      params: params
    })
  }
}
