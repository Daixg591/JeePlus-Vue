import request from '@/utils/httpRequest'

export default class TestActivitiExpenseService {
  save (inputForm) {
    return request({
      url: '/test/activiti/testActivitiExpense/save',
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/test/activiti/testActivitiExpense/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: '/test/activiti/testActivitiExpense/queryById',
      method: 'get',
      params: {id: id}
    })
  }

  list (params) {
    return request({
      url: '/test/activiti/testActivitiExpense/list',
      method: 'get',
      params: params
    })
  }
}
