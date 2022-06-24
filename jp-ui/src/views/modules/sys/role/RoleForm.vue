<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    width="700px"
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" status-icon v-loading="loading" ref="inputForm" @keyup.enter.native="inputFormSubmit()"
             label-width="120px" @submit.native.prevent>
       <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="角色名称" prop="name" :rules="[
            {required: true, message: '角色名称不能为空', trigger: 'blur'},
            {validator: validateName, trigger: 'blur'}
          ]">
            <el-input v-model="inputForm.name" placeholder="角色名称" maxlength="50"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="英文名称" prop="enName" :rules="[
            {required: true, message: '角色英文名称不能为空', trigger: 'blur'},
            {validator: validateEnName, trigger: 'blur'}
          ]">
            <el-input v-model="inputForm.enName" maxlength="50" placeholder="角色英文名称"></el-input>

          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否系统数据" prop="sysData" :rules="[
            {required: true, message: '是否系统数据不能为空', trigger: 'blur'}
          ]">
            <el-select v-model="inputForm.sysData" placeholder="请选择"  style="width: 100%;">
              <el-option
                v-for="item in this.$dictUtils.getDictList('yes_no')"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <div class="help-block">“是”代表此数据只有超级管理员能进行修改</div>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否可用" prop="useable" :rules="[
            {required: true, message: '是否可用不能为空', trigger: 'blur'}
          ]">
            <el-select v-model="inputForm.useable" placeholder="请选择"  style="width: 100%;">
              <el-option
                v-for="item in this.$dictUtils.getDictList('yes_no')"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <div class="help-block">“是”代表此数据可用，“否”则表示此数据不可用</div>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remarks">
            <el-input type="textarea" v-model="inputForm.remarks" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" @click="inputFormSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import RoleService from '@/api/sys/RoleService'
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        title: '',
        oldName: '',
        oldEnName: '',
        inputForm: {
          id: '',
          name: '',
          enName: '',
          remarks: '',
          sysData: '1',
          useable: '1'
        }
      }
    },
    roleService: null,
    created () {
      this.roleService = new RoleService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建角色`
        } else if (method === 'edit') {
          this.title = '修改角色'
        } else if (method === 'view') {
          this.title = '查看角色'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.oldName = ''
          this.oldEnName = ''
          if (this.inputForm.id) {
            this.loading = true
            let p3 = this.roleService.queryById(this.inputForm.id)
            Promise.all([p3]).then((result) => {
              let data = result[0].data
              this.loading = false
              this.inputForm = this.recover(this.inputForm, data)
              this.oldName = data.name
              this.oldEnName = data.enName
            })
          }
        })
      },
      validateName  (rule, value, callback) {
        if (this.oldName === '' || this.oldName !== this.inputForm.name) {
          this.roleService.validateNotExist({name: this.inputForm.name}).then(({data}) => {
            if (data === true) {
              callback()
            } else {
              callback(new Error('角色名称已存在!'))
            }
          })
        } else {
          callback()
        }
      },
      validateEnName  (rule, value, callback) {
        if (this.oldEnName === '' || this.oldEnName !== this.inputForm.enName) {
          this.roleService.validateNotExist({enName: this.inputForm.enName}).then(({data}) => {
            if (data === true) {
              callback()
            } else {
              callback(new Error('角色英文名已存在!'))
            }
          })
        } else {
          callback()
        }
      },
      // 表单提交
      inputFormSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.roleService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message.success(data)
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>
