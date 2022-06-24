import request from '@/utils/httpRequest'

export default class FormService {
  submitStartFormData (inputForm) {
    return request({
      url: '/flowable/form/submitStartFormData',
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
      data: inputForm
    })
  }

  submitTaskFormData (inputForm) {
    return request({
      url: '/flowable/form/submitTaskFormData',
      method: 'post',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
      data: inputForm
    })
  }

  getStartFormData (params) {
    return request({
      url: '/flowable/form/getStartFormData',
      method: 'get',
      params: params
    })
  }
  getHistoryTaskFormData (params) {
    return request({
      url: '/flowable/form/getHistoryTaskFormData',
      method: 'get',
      params: params
    })
  }

  getTaskFormData (params) {
    return request({
      url: '/flowable/form/getTaskFormData',
      method: 'get',
      params: params
    })
  }
}
