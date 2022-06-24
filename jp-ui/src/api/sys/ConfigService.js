import request from '@/utils/httpRequest'

export default class ConfigService {
  getConfig () {
    return request({
      url: `/sys/sysConfig/getConfig`,
      method: 'get'
    })
  }

  queryById () {
    return request({
      url: `/sys/sysConfig/queryById`,
      method: 'get'
    })
  }

  save (inputForm) {
    return request({
      url: `/sys/sysConfig/save`,
      method: 'post',
      data: inputForm
    })
  }
}
