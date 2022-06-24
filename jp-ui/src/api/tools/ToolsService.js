import request from '@/utils/httpRequest'

export default class ToolsService {
  sendEmail (inputForm) {
    return request({
      url: `/tools/email/send`,
      method: 'post',
      data: inputForm
    })
  }

  createTwoDimensionCode (params) {
    return request({
      url: `/tools/TwoDimensionCodeController/createTwoDimensionCode`,
      method: 'get',
      params: params
    })
  }
}
