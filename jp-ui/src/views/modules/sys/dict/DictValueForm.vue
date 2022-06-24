<template>
  <el-dialog
    :title="!inputForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    append-to-body
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" :rules="dataRule" v-loading="loading" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="80px" @submit.native.prevent>
      <el-form-item label="标签" prop="label">
        <el-input v-model="inputForm.label" placeholder="标签"></el-input>
      </el-form-item>
      <el-form-item label="键值" prop="value">
        <el-input v-model="inputForm.value" placeholder="键值"></el-input>
      </el-form-item>
      <el-form-item label="排序号" prop="sort">
        <el-input-number  :step="1" v-model="inputForm.sort" placeholder="排序号"></el-input-number>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
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
        inputForm: {
          id: '',
          dictTypeId: '',
          label: '',
          value: '',
          sort: 1
        },
        dataRule: {
          label: [
            {required: true, message: '标签不能为空', trigger: 'blur'}
          ],
          value: [
            {required: true, message: '键值不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    dictService: null,
    created () {
      this.dictService = new DictService()
    },
    methods: {
      init (method, obj) {
        this.inputForm.id = obj.dictValueId
        this.inputForm.dictTypeId = obj.dictTypeId
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.dictService.queryDictValue(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inputForm.id = obj.dictValueId
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        if (!this.groupId) {
          this.groupWrong = '请选择分组'
        }
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.dictService.saveDictValue(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message.success(data)
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
