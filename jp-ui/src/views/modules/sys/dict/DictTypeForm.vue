<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    append-to-body
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" :rules="dataRule" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="80px" @submit.native.prevent>
      <el-form-item label="类型" prop="type">
        <el-input v-model="inputForm.type" maxlength="50" placeholder="类型"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input type="textarea" v-model="inputForm.description" maxlength="50" placeholder="描述"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" v-if="method != 'view'" type="primary" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import DictService from '@/api/sys/DictService'
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        title: '',
        method: '',
        inputForm: {
          id: '',
          type: '',
          description: ''
        },
        dataRule: {
          type: [
            {required: true, message: '类型不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    dictService: null,
    created () {
      this.dictService = new DictService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新增字典`
        } else if (method === 'edit') {
          this.title = '编辑字典'
        } else if (method === 'view') {
          this.title = '查看字典'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.dictService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.dictService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
              this.$emit('refreshDataList')
              this.$dictUtils.refreshDictList()
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>
