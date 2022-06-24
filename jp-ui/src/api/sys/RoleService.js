import request from '@/utils/httpRequest'

export default class RoleService {
  save (inputForm) {
    return request({
      url: `/sys/role/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/sys/role/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/sys/role/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  validateNotExist (obj) {
    return request({
      url: `/sys/role/validateNotExist`,
      method: 'get',
      params: obj
    })
  }

  list (params) {
    return request({
      url: '/sys/role/list',
      method: 'get',
      params: params
    })
  }
  assign (params) {
    return request({
      url: '/sys/role/assign',
      method: 'get',
      params: params
    })
  }

  removeUserFromRole (userId, roleId) {
    return request({
      url: '/sys/role/removeUserFromRole',
      method: 'delete',
      params: {userId: userId, roleId: roleId}
    })
  }

  addUserToRole (roleId, userIds) {
    return request({
      url: '/sys/role/addUserToRole',
      method: 'put',
      params: {
        roleId: roleId,
        userIds: userIds
      }
    })
  }
}
