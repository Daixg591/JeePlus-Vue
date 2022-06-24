<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="标题" prop="title"
                :rules="[
                 ]">
              <el-input v-model="inputForm.title" placeholder="请填写标题"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="富文本1" prop="contents1"
                :rules="[
                 ]">
            <WangEditor ref="contents1Editor" v-model="inputForm.contents1"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="富文本2" prop="contents2"
                :rules="[
                 ]">
            <vue-editor useCustomImageHandler @image-added="$utils.handleImageAdded" v-model="inputForm.contents2"/>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import WangEditor from '@/components/editor/WangEditor'
  import TestNoteService from '@/api/test/note/TestNoteService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          title: '',
          contents1: '',
          contents2: ''
        }
      }
    },
    components: {
      WangEditor
    },
    testNoteService: null,
    created () {
      this.testNoteService = new TestNoteService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建富文本测试`
        } else if (method === 'edit') {
          this.title = '修改富文本测试'
        } else if (method === 'view') {
          this.title = '查看富文本测试'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.$refs.contents1Editor.init('')
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.testNoteService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.$refs.contents1Editor.init(this.inputForm.contents1)
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
            this.testNoteService.save(this.inputForm).then(({data}) => {
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

  
