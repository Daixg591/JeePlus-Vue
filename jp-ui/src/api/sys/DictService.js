import request from '@/utils/httpRequest'

export default class DictService {
  queryById (id) {
    return request({
      url: `/sys/dict/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  save (inputForm) {
    return request({
      url: `/sys/dict/save`,
      method: 'post',
      data: inputForm
    })
  }

  list (params) {
    return request({
      url: '/sys/dict/type/list',
      method: 'get',
      params: params
    })
  }

  delete (ids) {
    return request({
      url: '/sys/dict/delete',
      method: 'delete',
      params: {'ids': ids}
    })
  }

  queryDictValue (id) {
    return request({
      url: `/sys/dict/queryDictValue`,
      method: 'get',
      params: {dictValueId: id},
      loading: false
    })
  }

  saveDictValue (inputForm) {
    return request({
      url: `/sys/dict/saveDictValue`,
      method: 'post',
      data: inputForm
    })
  }

  getDictValue (dictTypeId) {
    return request({
      url: '/sys/dict/getDictValue',
      method: 'get',
      params: {
        'dictTypeId': dictTypeId
      }
    })
  }

  getDictMap (dictTypeId) {
    return request({
      url: '/sys/dict/getDictMap',
      method: 'get',
      params: {
        'dictTypeId': dictTypeId
      }
    })
  }

  deleteDictValue (ids) {
    return request({
      url: '/sys/dict/deleteDictValue',
      method: 'delete',
      params: {ids: ids}
    })
  }
}
