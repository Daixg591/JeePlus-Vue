<template>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">
  <el-tabs v-model="activeName" >
    <el-tab-pane label="登录" name="first">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <h3>单一登陆配置</h3>
        </div>
        <el-form size="small" ref="loginFormSetting" :model="loginFormSetting">
          <el-form-item>
             <el-checkbox true-label="0" false-label="1" v-model="loginFormSetting.multiAccountLogin">单一登录</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-radio-group v-model="loginFormSetting.singleLoginType">
              <el-radio :disabled="loginFormSetting.multiAccountLogin ==='1'" label="1">后登陆踢出先登录</el-radio>
              <el-radio :disabled="loginFormSetting.multiAccountLogin ==='1'" label="2">已登陆禁止再登陆</el-radio>
            </el-radio-group>
          </el-form-item>
           <el-form-item>
            <el-button size="small" type="primary" @click="doSubmit(loginFormSetting)">保存</el-button>
          </el-form-item>
        </el-form>
         
          
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="外观" name="second">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <h3>外观配置</h3>
        </div>
        <el-form size="small" label-width="150px" :model="themeFormSetting">
          <el-form-item label="产品标题">
            <el-input v-model="themeFormSetting.productName"></el-input>
          </el-form-item>
          <el-form-item label="产品logo">
            <el-upload
              class="avatar-uploader"
              :action="`${$http.BASE_URL}/sys/file/webupload/upload?uploadPath=logo`"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
               :show-file-list="false">
              <img v-if="themeFormSetting.logo" :src="themeFormSetting.logo" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="默认布局">
              <el-select v-model="themeFormSetting.defaultLayout" placeholder="请选择"  style="width: 100%;">
                <el-option
                  v-for="item in layouts"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="默认主题">
            <div class="tag-group" style="margin-top:7px">
              <el-tooltip effect="dark" :content="item.key" placement="top-start" v-for="(item, index) in colorList" :key="index">
                <el-tag :color="item.color" class="themeColorTag" @click="themeFormSetting.defaultTheme = item.color">
                  <i class="el-icon-check themeColorFont" v-if="item.color === themeFormSetting.defaultTheme"></i>
                </el-tag>
              </el-tooltip>
            </div>
          </el-form-item>
          <el-form-item label="首页地址">
              <el-input v-model="themeFormSetting.homeUrl"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="doSubmit(themeFormSetting)">保存</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="短信" name="third">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <h3>阿里大鱼短信配置(<a href="https://dayu.aliyun.com" target="_blank">官网</a>)</h3>
        </div>
        <el-form size="small" label-width="150px" :model="smsFormSetting">
          <el-form-item label="accessKeyId" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="smsFormSetting.accessKeyId" placeholder="此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)"></el-input>
          </el-form-item>
          <el-form-item label="accessKeySecret" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="smsFormSetting.accessKeySecret" placeholder="此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)"></el-input>
          </el-form-item>
          <el-form-item label="短信签名" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="smsFormSetting.signature" placeholder="必填:短信签名-可在短信控制台中找到"></el-input>
          </el-form-item>
          <el-form-item label="短信模板" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="smsFormSetting.templateCode" placeholder="必填:短信模板编号-可在短信控制台中找到"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="doSubmit(smsFormSetting)">保存</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="邮箱" name="fourth">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <h3>发件人账户</h3>
        </div>
         <el-form size="small" label-width="150px" :model="emailFormSetting">
          <el-form-item label="邮箱服务器地址" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="emailFormSetting.smtp" placeholder=" 邮件服务器的SMTP地址，默认为smtp.<发件人邮箱后缀>"></el-input>
          </el-form-item>
          <el-form-item label="邮箱服务器端口" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="emailFormSetting.port" placeholder="邮件服务器的SMTP端口，默认25"></el-input>
          </el-form-item>
          <el-form-item label="系统邮箱地址" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="emailFormSetting.mailName" placeholder="用户名，默认为发件人邮箱前缀"></el-input>
          </el-form-item>
          <el-form-item label="系统邮箱密码" :rules="[
                  {required: true, message:'必填项不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="emailFormSetting.mailPassword" placeholder="密码（注意，某些邮箱需要为SMTP服务单独设置密码，详情查看相关帮助）"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="primary" @click="doSubmit(emailFormSetting)">保存</el-button>
          </el-form-item>
        </el-form>

      </el-card>
    </el-tab-pane>
  </el-tabs>
  </div>
</div>
</template>
<script>
  import ConfigService from '@/api/sys/ConfigService'
  export default {
    data () {
      return {
        activeName: 'first',
        loginFormSetting: {
          id: '',
          multiAccountLogin: '1',
          singleLoginType: '1'
        },
        themeFormSetting: {
          id: '',
          defaultTheme: '#1890FF',
          productName: '',
          logo: '',
          defaultLayout: '',
          homeUrl: ''
        },
        smsFormSetting: {
          id: '',
          accessKeyId: '',
          accessKeySecret: '',
          signature: '',
          templateCode: ''
        },
        emailFormSetting: {
          id: '',
          smtp: '',
          port: '',
          mailName: '',
          mailPassword: ''
        },
        colorList: [
          {
            key: '拂晓蓝（默认）', color: '#1890FF'
          },
          {
            key: '薄暮', color: '#F5222D', label: '1'
          },
          {
            key: '火山', color: '#FA541C', label: '2'
          },
          {
            key: '日暮', color: '#FAAD14'
          },
          {
            key: '明青', color: '#13C2C2'
          },
          {
            key: '极光绿', color: '#52C41A'
          },
          {
            key: '极客蓝', color: '#2F54EB'
          },
          {
            key: '酱紫', color: '#722ED1'
          },
          {
            key: '天空蓝', color: '#3e8df7'
          },
          {
            key: '咖啡色', color: '#9a7b71'
          },
          {
            key: '深湖蓝', color: '#07b2d3'
          },
          {
            key: '原谅绿', color: '#0cc26c'
          },
          {
            key: '古铜灰', color: '#757575'
          },
          {
            key: '珊瑚紫', color: '#6779fa'
          },
          {
            key: '橙黄', color: '#eb6607'
          },
          {
            key: '粉红', color: '#f74584'
          },
          {
            key: '青紫', color: '#9463f7'
          },
          {
            key: '橄榄绿', color: '#16b2a3'
          }
        ],
        layouts: [
          {label: '横向菜单', value: 'top'},
          {label: '左侧菜单', value: 'left'}
        ]
      }
    },
    configService: null,
    created () {
      this.configService = new ConfigService()
    },
    activated () {
      this.configService.queryById().then(({data}) => {
        this.loginFormSetting = this.recover(this.loginFormSetting, data)
        this.themeFormSetting = this.recover(this.themeFormSetting, data)
        this.smsFormSetting = this.recover(this.smsFormSetting, data)
        this.emailFormSetting = this.recover(this.emailFormSetting, data)
      })
    },
    methods: {
      handleAvatarSuccess (res, file) {
        this.themeFormSetting.logo = res.url
      },

      beforeAvatarUpload (file) {
        const isJPG = file.type.indexOf('image/') >= 0
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传LOGO只能是图片格式!')
          return false
        }
        if (!isLt2M) {
          this.$message.error('上传LOGO大小不能超过 2MB!')
          return false
        }
        return true
      },
      doSubmit (form) {
        this.configService.save(form).then(({data}) => {
          this.$message.success(data)
          localStorage.setItem('defaultLayout', data.config.defaultLayout)
          localStorage.setItem('defaultTheme', data.config.defaultTheme)
          this.$events.$emit('updateTheme', data.config.defaultTheme)
          this.$store.commit('config/updateConfig', data.config)
        })
      }
    }
  }
</script>
<style>
.themeColorTag{
  width:25px !important; 
  height:25px !important; 
}
 .themeColorTag.el-tag + .el-tag {
    margin-left: 5px;
    margin-bottom: 0px;
}
.themeColorFont{
  position: absolute;
  color: #fff;
  margin-top: 3px;
  margin-left: -6px;
  font-weight: bold;
  font-size: 16px;
}
</style>