<template>
  <el-dialog
    :close-on-click-modal="false"
    title="流程催办"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" ref="inputForm" :model="inputForm"   v-loading="loading" @keyup.enter.native="inputFormSubmit()" label-width="120px" @submit.native.prevent> 
       <el-form-item label="催办方式" prop="checkedUrgeTypes"  :rules="[
                  {required: true, message:'催办方式不能为空', trigger:'blur'}
                 ]">
          <el-checkbox-group v-model="inputForm.checkedUrgeTypes">
            <el-checkbox v-for="urgeType in urgeTypes" :label="urgeType" :key="urgeType">{{urgeType}}</el-checkbox>
          </el-checkbox-group>
      </el-form-item>
      <el-form-item label="催办内容" prop="content"  :rules="[
                  {required: true, message:'催办内容不能为空', trigger:'blur'}
                 ]">
            <el-input type="textarea" v-model="inputForm.content" :rows="4" placeholder="请输入催办内容"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" @click="inputFormSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        urgeTypes: ['系统通知', '站内信', '短信', '邮件'],
        inputForm: {
          checkedUrgeTypes: ['系统通知', '站内信'],
          content: ''
        }
      }
    },
    methods: {
      init () {
        this.visible = true
        this.$refs.inputForm.resetFields()
      },
      // 表单提交
      inputFormSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.$message.success('催办成功')
            this.visible = false
            this.loading = false
          }
        })
      }
    }
  }
</script>
