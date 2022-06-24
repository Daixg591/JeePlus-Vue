<template>
  <el-dialog
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" v-loading="loading" status-icon ref="inputForm" @keyup.enter.native="inputFormSubmit()"
             label-width="120px" @submit.native.prevent>
       <el-row :gutter="15">
        <el-col :span="12">

           <el-form-item label="流程分类" prop="category" :rules="[
            {required: true, message: '流程分类不能为空', trigger: 'blur'}
          ]">
          <el-select v-model="inputForm.category" placeholder="请选择"  style="width: 100%;">
              <el-option
                v-for="item in this.$dictUtils.getDictList('act_category')"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="模型标识" prop="key" :rules="[{required: true, message: '模型标识不能为空', trigger: 'blur'}]">
            <el-input v-model="inputForm.key" maxlength="50" placeholder=""></el-input>
          </el-form-item>
        </el-col>
    
        <el-col :span="12">
          <el-form-item label="模型名称" prop="name" :rules="[
            {required: true, message: '模型标识不能为空', trigger: 'blur'}
          ]">
            <el-input v-model="inputForm.name" maxlength="50" placeholder=""></el-input>
          </el-form-item>
        </el-col>


        <el-col :span="12">
          <el-form-item label="模块描述" prop="description">
            <el-input type="textarea" v-model="inputForm.description" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
       
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button type="primary" @click="inputFormSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import AppModelsService from '@/api/flowable/AppModelsService'
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          category: '',
          modelType: 0,
          key: '',
          name: '',
          description: ''
        }
      }
    },
    appModelsService: null,
    created () {
      this.appModelsService = new AppModelsService()
    },
    methods: {
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
        })
      },
      // 表单提交
      inputFormSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.appModelsService.models(this.inputForm).then(({data}) => {
              this.$emit('create', data)
              this.visible = false
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>
