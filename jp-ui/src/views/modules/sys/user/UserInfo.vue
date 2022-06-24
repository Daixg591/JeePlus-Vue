<template>
<div class="el-scrollbar__wrap wrap-white padding-20">
  <div class="el-scrollbar__view">
<el-tabs tab-position="left">
    <el-tab-pane label="基本信息">
      <h3 style="margin-left:40px">基本信息</h3>
      <el-form size="small" :model="generalForm" ref="generalForm" label-width="100px">
        <el-form-item label="姓名" :rules="[ { required: true, message: '必填项不能为空', trigger: 'blur' }]" prop="name">
          <el-input v-model="generalForm.name"></el-input>
        </el-form-item>
         <el-form-item label="签名" prop="sign">
          <el-input v-model="generalForm.sign"></el-input>
        </el-form-item>
         <el-form-item label="公司" prop="companyDTO.name">
          <el-input v-model="generalForm.companyDTO.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="officeDTO.name">
          <el-input v-model="generalForm.officeDTO.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="generalForm.remarks"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="submitGeneralForm()">保存</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="联系方式">
      <h3 style="margin-left:40px">联系方式</h3>
      <el-form size="small" :model="concatForm" ref="concatForm" label-width="100px">
        <el-form-item label="邮箱" :rules="[{type:'email', message:'请输入正确的邮箱地址', trigger:'blur'}]" prop="email">
          <el-input v-model="concatForm.email"></el-input>
        </el-form-item>
         <el-form-item label="手机" prop="mobile" :rules="[{validator:validator.isMobile, trigger:'blur'}]">
          <el-input v-model="concatForm.mobile"></el-input>
        </el-form-item>
         <el-form-item label="电话" prop="phone" :rules="[{validator:validator.isPhone, trigger:'blur'}]">
          <el-input v-model="concatForm.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button  size="small" type="primary" @click="submitConcatForm()">保存</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="我的头像">
      <h3 style="margin-left:40px">我的头像</h3>
      <el-upload
        class="avatar-uploader"
        :action="`${this.$http.BASE_URL}/sys/user/imageUpload`"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :headers="{token: $cookie.get('token')}"
        :before-upload="beforeAvatarUpload">
        <img v-if="photo" :src="photo" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </el-tab-pane>
    <el-tab-pane label="修改密码">
      <h3 style="margin-left:40px">修改密码</h3>
      <el-form size="small" :model="pwdForm" ref="pwdForm" label-width="100px">
        <el-form-item label="旧密码" :rules="[ { required: true, message: '必填项不能为空', trigger: 'blur' }]" prop="oldPassword">
          <el-input v-model="pwdForm.oldPassword" show-password></el-input>
        </el-form-item>
         <el-form-item label="新密码" :rules="[ { required: true, message: '必填项不能为空', trigger: 'blur' }]" prop="newPassword">
          <el-input v-model="pwdForm.newPassword" show-password></el-input>
        </el-form-item>
         <el-form-item label="确认新密码" :rules="rule" prop="confirmNewPassword">
          <el-input v-model="pwdForm.confirmNewPassword" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="submitPwdForm()">保存</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="我的日志">
        <el-menu  default-active="1" mode="horizontal" @select="changeLog">
          <el-menu-item index="1">
            <i class="el-icon-setting"></i>
            <span slot="title">登陆日志</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="el-icon-setting"></i>
            <span slot="title">访问日志</span>
          </el-menu-item>
        </el-menu>
       <vxe-table
            border="inner"
            auto-resize
            resizable
            height="500px"
            :loading="loading"
            size="small"
            ref="logTable"
            show-header-overflow
            show-overflow
            highlight-hover-row
            :menu-config="{}"
            :print-config="{}"
            :import-config="{}"
            :export-config="{}"
            @sort-change="sortChangeHandle"
            :sort-config="{remote:true}"
            :data="dataList"
            :checkbox-config="{}">
          <vxe-column type="seq" width="40"></vxe-column>
          <vxe-column title="操作菜单" field="title" sortable></vxe-column>
          <vxe-column title="耗时(毫秒)" field="recordTime" sortable></vxe-column>
          <vxe-column title="请求参数" field="params" sortable></vxe-column>
          <vxe-column title="返回结果" field="result" sortable></vxe-column>
          <vxe-column title="公司" field="createBy.companyDTO.name" sortable></vxe-column>
          <vxe-column title="部门" field="createBy.officeDTO.name" sortable></vxe-column>
          <vxe-column title="用户" field="createBy.name" sortable></vxe-column>
          <vxe-column title="IP地址" field="remoteAddr" sortable></vxe-column>
          <vxe-column title="访问时间" field="createDate" sortable></vxe-column>
        </vxe-table>
        <vxe-pager
          background
          size="small"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.total"
          :page-sizes="[10, 20, 100, 1000, {label: '全量数据', value: 1000000}]"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
          @page-change="currentChangeHandle">
        </vxe-pager>
    </el-tab-pane>
  </el-tabs>
</div>
</div>
</template>

<script>
  import UserService from '@/api/sys/UserService'
  import LogService from '@/api/sys/LogService'
  export default {
    data () {
      var validatePass2 = (rule, value, callback) => {
        if (value !== this.pwdForm.newPassword) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      return {
        type: '1',
        generalForm: {
          id: '',
          name: '',
          sign: '',
          companyDTO: {
            name: ''
          },
          officeDTO: {
            name: ''
          },
          remarks: ''
        },
        concatForm: {
          id: '',
          email: '',
          phone: '',
          mobile: ''
        },
        pwdForm: {
          id: '',
          oldPassword: '',
          newPassword: '',
          confirmNewPassword: ''
        },
        photo: '',
        dataList: [],
        tablePage: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          orders: [{column: 'create_date', asc: false}]
        },
        loading: false,
        rule: [{ required: true, message: '必填项不能为空', trigger: 'blur' }, {validator: validatePass2, trigger: 'blur'}]
      }
    },
    userService: null,
    logService: null,
    created () {
      this.userService = new UserService()
      this.logService = new LogService()
    },
    watch: {
      '$store.state.user.id': {
        handler (userId) {
          if (userId) {
            this.userService.queryById(userId).then(({data}) => {
              this.recover(this.generalForm, data)
              this.recover(this.concatForm, data)
              this.photo = data.photo
            })
            this.refreshList()
          }
        },
  
        immediate: true,
        deep: false
      }
    },
    methods: {
      submitGeneralForm () {
        this.$refs['generalForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: 'Loading',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            this.userService.saveInfo(this.generalForm).then(({data}) => {
              loading.close()
              this.$store.commit('user/updateName', this.generalForm.name)
              this.$message.success(data)
            })
          }
        })
      },
      submitConcatForm () {
        this.$refs['concatForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: 'Loading',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            this.userService.saveInfo(this.concatForm).then(({data}) => {
              this.$message.success(data)
              loading.close()
            })
          }
        })
      },
      submitPwdForm () {
        this.$refs['pwdForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: 'Loading',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            this.userService.savePwd(this.pwdForm).then(({data}) => {
              this.$message.success(data)
              loading.close()
            }).catch(() => {
              loading.close()
            })
          }
        })
      },
 // 获取数据列表
      refreshList () {
        this.loading = true
        this.logService.mine({
          'current': this.tablePage.currentPage,
          'size': this.tablePage.pageSize,
          'orders': this.tablePage.orders
        }).then(({data}) => {
          this.dataList = data.records
          this.tablePage.total = data.total
          this.loading = false
        })
      },
      changeLog (index) {
        this.type = index
        this.refreshList()
      },
      // 当前页
      currentChangeHandle ({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.refreshList()
      },
      // 排序
      sortChangeHandle (column) {
        if (column.property === 'createBy.companyDTO.name') {
          column.property = 'c.name'
        } else if (column.property === 'createBy.officeDTO.name') {
          column.property = 'o.name'
        } else if (column.property === 'createBy.name') {
          column.property = 'u.name'
        }
        this.tablePage.orders = []
        if (column.order != null) {
          this.tablePage.orders.push({column: this.$utils.toLine(column.property), asc: column.order === 'asc'})
        }
        this.refreshList()
      },
      handleAvatarSuccess (res, file) {
        this.photo = res.path
        this.$message.success(res.msg)
        this.$store.commit('user/updatePhoto', res.path)
      },

      beforeAvatarUpload (file) {
        const isJPG = file.type.indexOf('image/') >= 0
        const isLt2M = file.size / 1024 / 1024 < 5

        if (!isJPG) {
          this.$message.error('上传头像只能是图片格式!')
          return false
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
          return false
        }
        return true
      }
    }
  }
</script>

