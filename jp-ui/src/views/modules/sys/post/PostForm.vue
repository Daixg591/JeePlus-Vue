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
        <el-col :span="12">
            <el-form-item label="岗位名称" prop="name"
                :rules="[
                  {required: true, message:'岗位名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写岗位名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="岗位编码" prop="code"
                :rules="[
                  {required: true, message:'岗位编码不能为空', trigger:'blur'},
                  {validator: validator.isRightfulString, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.code" placeholder="请填写岗位编码"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="是否可用" prop="status"
                :rules="[
                  {required: true, message:'岗位状态不能为空', trigger:'blur'}
                 ]">
                    <el-radio-group v-model="inputForm.status">
                        <el-radio v-for="item in $dictUtils.getDictList('yes_no')" :label="item.value" :key="item.id">{{item.label}}</el-radio>
                    </el-radio-group>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="岗位排序" prop="sort"
                :rules="[
                  {required: true, message:'岗位排序不能为空', trigger:'blur'},
                  {validator: validator.isIntGtZero, trigger:'blur'}
                 ]">
              <el-input-number v-model="inputForm.sort" :min="1" :max="1000" label="请填写岗位排序"></el-input-number>
           </el-form-item>
        </el-col>
         <el-col :span="12">
            <el-form-item label="岗位类型" prop="type"
                :rules="[
                  {required: true, message:'岗位类型不能为空', trigger:'blur'}
                 ]">
                <el-select v-model="inputForm.type" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in $dictUtils.getDictList('sys_post_type')"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
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
  import PostService from '@/api/sys/PostService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          name: '',
          code: '',
          type: '',
          status: '',
          sort: '',
          remarks: ''
        }
      }
    },
    postService: null,
    created () {
      this.postService = new PostService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建岗位`
        } else if (method === 'edit') {
          this.title = '修改岗位'
        } else if (method === 'view') {
          this.title = '查看岗位'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.postService.queryById(this.inputForm.id).then(({data}) => {
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
            this.postService.save(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>