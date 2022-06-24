import request from '@/utils/httpRequest'

export default class FileService {
  uploadFile (formData, uploadPath) {
    return request({
      url: `/sys/file/webupload/upload?uploadPath=${uploadPath}`,
      method: 'post',
      data: formData,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }

  getUrl (params) {
    return request({
      url: `/sys/file/getUrl`,
      method: 'get',
      params: params
    })
  }
}
