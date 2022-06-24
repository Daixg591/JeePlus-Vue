<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="120px" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'" @submit.native.prevent>
    <el-divider content-position="left"><i class="el-icon-lock"></i> 登录信息</el-divider>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="账号"  prop="loginName" :rules="[{required: true, message: '登录名不能为空', trigger: 'blur'}]">
                <el-input v-model="inputForm.loginName" maxlength="50" placeholder=""></el-input>
              </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码:" prop="newPassword" :rules="inputForm.id?[]:[{required: true, message:'密码不能为空', trigger:'blur'}]">
                <el-input v-model="inputForm.newPassword" maxlength="50" placeholder="若不修改，请留空" show-password></el-input>
              </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="确认密码" prop="confirmNewPassword" :rules="inputForm.id?[{validator: validatePass2, trigger: 'blur'}]:[{required: true, message:'确认密码不能为空', trigger:'blur'},{validator: validatePass2, trigger: 'blur'}]">
              <el-input v-model="inputForm.confirmNewPassword" maxlength="50" placeholder="" show-password></el-input>
            </el-form-item>
          </el-col>
        <el-col :span="12">
          <el-form-item label="是否允许登录" prop="loginFlag" :rules="[{required: true, message: '必须选择', trigger: 'blur'}]">
            <el-radio-group v-model="inputForm.loginFlag">
              <el-radio v-for="item in this.$dictUtils.getDictList('yes_no')" :label="item.value" :key="item.id">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
    </el-row>
     <el-divider content-position="left"><i class="el-icon-user"></i> 身份信息</el-divider>
         <el-row :gutter="15">
        <!-- <el-col :span="24"> -->
          <!-- <el-form-item prop="photo" label="头像"> 
            <el-upload
              class="avatar-uploader"
              :action="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=photo`"
              :headers="{token: $cookie.get('token')}"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
               :show-file-list="false">
              <img v-if="inputForm.photo" :src="inputForm.photo" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-col> -->
        <el-col :span="12">
            <el-form-item label="姓名" :rules="[{required: true, message: '姓名不能为空', trigger: 'blur'}]" prop="name">
              <el-input v-model="inputForm.name" maxlength="50" placeholder=""></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机" prop="mobile" :rules="[{validator:validator.isMobile, trigger:'blur'}]">
            <el-input v-model="inputForm.mobile" maxlength="50" placeholder=""></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
           <el-form-item label="电话" prop="phone" :rules="[{validator:validator.isPhone, trigger:'blur'}]">
            <el-input v-model="inputForm.phone" maxlength="50" placeholder=""></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
           <el-form-item label="邮箱" prop="email" :rules="[{type:'email', message:'请输入正确的邮箱地址', trigger:'blur'}]">
            <el-input v-model="inputForm.email" maxlength="50" placeholder=""></el-input>
           </el-form-item>
        </el-col>
    </el-row>
    <el-divider content-position="left"><i class="el-icon-postcard"></i> 岗位信息</el-divider>
        <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item prop="companyDTO.id" :rules=" [{required: true, message: '公司不能为空', trigger: 'blur'}]" label="公司"> 
            <SelectTree 
              ref="companyTree"
              :props="{
                  value: 'id',             // ID字段名
                  label: 'name',         // 显示名称
                  children: 'children'    // 子级字段名
                }"
              :url="`/sys/office/treeData?type=1&&extId=${this.inputForm.id}`"
              :value="inputForm.companyDTO.id"
              :clearable="true" 
              :accordion="true"
              @getValue="(value) => {inputForm.companyDTO.id=value}"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
           <el-form-item prop="officeDTO.id" :rules="[{required: true, message: '部门不能为空', trigger: 'blur'}]" label="部门">
            <SelectTree 
              ref="officeTree"
              :props="{
                  value: 'id',             // ID字段名
                  label: 'name',         // 显示名称
                  children: 'children'    // 子级字段名
                }"
               
              :url="`/sys/office/treeData?type=2&&extId=${this.inputForm.id}`"
              :value="inputForm.officeDTO.id"
              :clearable="true" 
              :accordion="true"
              @getValue="(value) => {inputForm.officeDTO.id=value}"/>
          </el-form-item>
        </el-col>
        
        <el-col :span="12">
            <el-form-item label="工号" prop="no" :rules="[{required: true, message:'工号不能为空', trigger:'blur'}]">
              <el-input v-model="inputForm.no" maxlength="50" placeholder=""></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岗位" prop="postIdList">
               <el-select v-model="inputForm.postIdList" style="width:100%" multiple placeholder="请选择">
                  <el-option
                    v-for="item in postList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
              </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色" prop="roleIdList" :rules="[{required: true, message: '角色不能为空', trigger: 'blur'}]">
              <el-select v-model="inputForm.roleIdList" style="width:100%" multiple placeholder="请选择">
                <el-option
                  v-for="role in roleList"
                  :key="role.id"
                  :label="role.name"
                  :value="role.id">
                </el-option>
              </el-select>
          </el-form-item>
        </el-col>
         <el-col :span="12">
           <el-form-item label="备注" prop="remarks">
            <el-input type="textarea" v-model="inputForm.remarks"  maxlength="200" placeholder=""></el-input>
           </el-form-item>
        </el-col>
    </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SelectTree from '@/components/treeSelect/treeSelect.vue'
import UserService from '@/api/sys/UserService'
import PostService from '@/api/sys/PostService'
import RoleService from '@/api/sys/RoleService'
export default {
  data () {
    return {
      visible: false,
      loading: false,
      title: '',
      method: '',
      roleList: [],
      postList: [],
      inputForm: {
        id: '',
        companyDTO: { // 归属公司
          id: '',
          name: ''
        },
        officeDTO: {
          id: '',
          name: ''
        },
        roleIdList: [],
        postIdList: [],
        loginName: '', // 登录名
        no: '', // 工号
        name: '', // 姓名
        email: '', // 邮箱
        phone: '', // 电话
        mobile: '', // 手机
        loginFlag: '1', // 是否允许登陆
        photo: '', // 头像
        qrCode: '', // 二维码
        oldLoginName: '', // 原登录名
        newPassword: '', // 新密码
        confirmNewPassword: '',
        sign: '', // 签名
        remarks: '' // 备注

      }
    }
  },
  userService: null,
  roleService: null,
  postService: null,
  created () {
    this.userService = new UserService()
    this.roleService = new RoleService()
    this.postService = new PostService()
  },
  mounted () {
  },
  components: {
    SelectTree
  },
  methods: {
    init (method, id) {
      this.method = method
      this.inputForm.id = id
      if (method === 'add') {
        this.title = `新建用户`
      } else if (method === 'edit') {
        this.title = '修改用户'
      } else if (method === 'view') {
        this.title = '查看用户'
      }
      this.visible = true
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields()
        this.inputForm.oldLoinName = ''
        this.roleService.list({current: 1, size: -1}).then(({data}) => {
          this.roleList = data.records
        })
        this.postService.list({current: 1, size: -1}).then(({data}) => {
          this.postList = data.records
        })

        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.userService.queryById(this.inputForm.id).then(({data}) => {
            this.inputForm = this.recover(this.inputForm, data)
            this.inputForm.oldLoginName = this.inputForm.loginName
          })
        }
      })
    },

    handleAvatarSuccess (res, file) {
      this.inputForm.photo = res.url
    },

    beforeAvatarUpload (file) {
      const isJPG = file.type.indexOf('image/') >= 0
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像只能是图片格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
        return false
      }
      return true
    },
      // 表单提交
    doSubmit () {
      this.$refs['inputForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.userService.save(this.inputForm).then(({data}) => {
            this.loading = false
            this.visible = false
            this.$message.success(data)
            this.$emit('refreshDataList')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    validatePass2  (rule, value, callback) {
      if (value !== this.inputForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
  }
}
</script>
<style scoped>
.avatar{
  height: 100px;
}
.el-divider__text {
    font-weight: bold;
    font-size: 16px;
}
</style>