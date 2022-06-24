import request from '@/utils/httpRequest'

export default class ProcessService {
  list (params) {
    return request({
      url: '/flowable/process/list',
      method: 'get',
      params: params
    })
  }

  runningDataList (params) {
    return request({
      url: '/flowable/process/runningData',
      method: 'get',
      params: params
    })
  }

  historyListData (params) {
    return request({
      url: '/flowable/process/historyListData',
      method: 'get',
      params: params
    })
  }

  revokeProcIns (id) {
    return request({
      url: `/flowable/process/revokeProcIns`,
      method: 'put',
      params: {id: id}
    })
  }

  deleteProcIns (ids, reason) {
    return request({
      url: '/flowable/process/deleteProcIns',
      method: 'delete',
      params: {
        ids: ids,
        reason: reason}
    })
  }

  deleteAllProcIns (ids) {
    return request({
      url: '/flowable/process/history/deleteAllProcIns',
      method: 'delete',
      params: {procInsIds: ids}
    })
  }

  suspend (procDefId) {
    return request({
      url: '/flowable/process/update/suspend',
      method: 'put',
      params: {procDefId: procDefId}
    })
  }

  active (procDefId) {
    return request({
      url: '/flowable/process/update/active',
      method: 'put',
      params: {procDefId: procDefId}
    })
  }

  stop (id, message) {
    return request({
      url: '/flowable/process/stop',
      method: 'put',
      params: {id: id, message: message}
    })
  }

  getFlowChart (processDefId) {
    return request({
      url: '/flowable/process/getFlowChart',
      method: 'get',
      params: {processDefId: processDefId}
    })
  }

  queryProcessStatus (procDefId, procInsId) {
    return request({
      url: '/flowable/process/queryProcessStatus',
      method: 'get',
      params: {procDefId: procDefId, procInsId: procInsId}
    })
  }

  exist (key) {
    return request({
      url: '/flowable/process/exist',
      method: 'get',
      params: {key: key}
    })
  }
}
