<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''" :disabled="method==='view'" v-loading="loading" @keyup.enter.native="doSubmit()"
             label-width="120px" @submit.native.prevent>
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="名称" prop="name"
                :rules="[
                  {required: true, message:'名称不能为空', trigger:'blur'}
                 ]">
			        <el-input v-model="inputForm.name" placeholder="请填写名称"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="表达式" prop="expression"
                :rules="[
                  {required: true, message:'表达式不能为空', trigger:'blur'}
                 ]">
			        <el-input v-model="inputForm.expression" placeholder="请填写表达式"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="备注" prop="remarks"
                :rules="[
                 ]">
					<el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注"     ></el-input>
	         </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import ConditionService from '@/api/flowable/ConditionService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        conditionTab: '0',
        inputForm: {
          id: '',
          name: '',
          expression: '',
          remarks: ''
        }
      }
    },
    conditionService: null,
    created () {
      this.conditionService = new ConditionService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建流程表达式`
        } else if (method === 'edit') {
          this.title = '修改流程表达式'
        } else if (method === 'view') {
          this.title = '查看流程表达式'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.conditionTab = '0'
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.conditionService.queryById(this.inputForm.id).then(({data}) => {
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
            this.conditionService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>