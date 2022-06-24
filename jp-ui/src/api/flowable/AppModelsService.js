import request from '@/utils/httpRequest'

export default class AppModelsService {
  models (data) {
    return request({
      url: '/app/rest/models',
      method: 'post',
      data: data
    })
  }

  editorJson (modelId) {
    return request({
      url: `/app/rest/models/${modelId}/editor/json?version=${new Date().getTime()}`,
      method: 'get'
    })
  }
}
