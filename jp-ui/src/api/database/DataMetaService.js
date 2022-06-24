import request from '@/utils/httpRequest'

export default class DataMetaService {
  queryNeedByDataSetId (id) {
    return request({
      url: `/database/datamodel/dataMeta/queryNeedByDataSetId`,
      method: 'get',
      params: {id: id}
    })
  }
}
