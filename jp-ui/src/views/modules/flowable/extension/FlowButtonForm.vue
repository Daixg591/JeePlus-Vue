<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm"  :class="method==='view'?'readonly':''" :disabled="method==='view'" @keyup.enter.native="doSubmit()"
             label-width="120px" @submit.native.prevent>
      <el-row  :gutter="15">
        <el-col :span="16">
            <el-form-item label="按钮名称" prop="name"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.name" placeholder="请填写按钮名称"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="编码" prop="code"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.code" placeholder="请填写编码"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="是否隐藏" prop="isHide"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.isHide" placeholder="请填写是否隐藏"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="下一节点审核人" prop="next"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.next" placeholder="请填写下一节点审核人"     ></el-input>
	         </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="排序" prop="sort"
                :rules="[
                 ]">
			        <el-input-number v-model="inputForm.sort" placeholder="请填写排序"  :step="10"  :min="0" :max="99999" style="width: 100%;"  ></el-input-number>
	         </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="任务节点外键" prop="taskDef.id"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.taskDef.id" placeholder="请填写任务节点外键"     ></el-input>
	         </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button v-if="method === 'add'" type="primary" @click="continueDoSubmit()">继续添加</el-button>
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button v-if="method !== 'view'" type="primary" icon="el-icon-circle-check" @click="doSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        inputForm: {
          id: '',
          name: '',
          code: '',
          isHide: '',
          next: '',
          sort: '',
          taskDef: {
            id: ''
          }
        }
      }
    },
    components: {
    },
    methods: {
      init (method, obj) {
        this.method = method
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          if (method === 'add') {
            this.title = '新增按钮设置'
            this.oldInputForm = ''
          } else if (method === 'edit') {
            this.title = '修改按钮设置'
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          } else if (method === 'view') {
            this.title = '查看按钮设置'
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          }
        })
      },
   // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.visible = false
          }
        })
      },
      continueDoSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.$refs['inputForm'].resetFields()
          }
        })
      }
    }
  }
</script>