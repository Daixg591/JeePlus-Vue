import request from '@/utils/httpRequest'

export default class DataScreenCategoryService {
  save (inputForm) {
    return request({
      url: `/datav/dataScreenCategory/save`,
      method: 'post',
      data: inputForm
    })
  }

  delete (ids) {
    return request({
      url: '/datav/dataScreenCategory/delete',
      method: 'delete',
      params: {ids: ids}
    })
  }

  queryById (id) {
    return request({
      url: `/datav/dataScreenCategory/queryById`,
      method: 'get',
      params: {id: id}
    })
  }

  treeData (extId) {
    return request({
      url: '/datav/dataScreenCategory/treeData',
      method: 'get',
      params: {extId: extId}
    })
  }
}
