<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="分类" prop="category.id"
                :rules="[
                  {required: true, message:'分类不能为空', trigger:'blur'}
                 ]">
          <SelectTree
                      ref="category"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"

                      url="/extension/formCategory/treeData"
                      :value="inputForm.category.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.category.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="表单名称" prop="name"
                :rules="[
                  {required: true, message:'表单名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写表单名称"     ></el-input>
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
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import FormDefinitionService from '@/api/flowable/FormDefinitionService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          category: {
            id: ''
          },
          name: ''
        }
      }
    },
    components: {
      SelectTree
    },
    formDefinitionService: null,
    created () {
      this.formDefinitionService = new FormDefinitionService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建流程表单`
        } else if (method === 'edit') {
          this.title = '修改流程表单'
        } else if (method === 'view') {
          this.title = '查看流程表单'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.formDefinitionService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.formDefinitionService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.visible = false
              this.$message.success(data.msg)
              if (this.method === 'add') {
                this.$emit('showDesignForm', data.id)
              }
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>