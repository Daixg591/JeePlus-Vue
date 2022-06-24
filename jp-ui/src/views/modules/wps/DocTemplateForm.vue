<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form :model="inputForm" ref="inputForm" size="small" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="文档名称" prop="name"
                  :rules="[
                  {required: true, message:'文档名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写文档名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="存储路径" prop="path"
                :rules="[
                  {required: true, message:'存储路径不能为空', trigger:'blur'}
                 ]">
              <el-upload ref="path"
                    v-if="visible"
                    :action="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=wps/docTemplate`"
                    :headers="{token: $cookie.get('token')}"
                    :on-preview="(file, fileList) => {$window.location.href = file.url}"
                    :on-success="(response, file, fileList) => {
                       inputForm.path = fileList.map(item => (item.response && item.response.url) || item.url).join('|')
                    }"
                    :on-error="(response) => {
                      $message.error(response.message)
                    }"
                    :on-remove="(file, fileList) => {
                      $http.delete(`/sys/file/webupload/deleteByUrl?url=${(file.response && file.response.url) || file.url}`).then(({data}) => {
                        $message.success(data)
                      })
                      inputForm.path = fileList.map(item => item.url).join('|')
                    }"
                    :before-remove="(file, fileList) => {
                      return $confirm(`确定移除 ${file.name}？`)
                    }"
                    multiple
                    :limit="1"
                    :on-exceed="(files, fileList) =>{
                      $message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
                    }"
                    :file-list="pathArra">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">添加相关附件</div>
                  </el-upload>
           </el-form-item>
        </el-col>

        <el-col :span="24">
            <el-form-item label="类型" prop="docCategoryDTO.id"
                :rules="[
                  {required: true, message:'类型不能为空', trigger:'blur'}
                 ]">
                   <SelectTree
                      ref="category"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"

                      url="/wps/docCategory/treeData"
                      :value="inputForm.docCategoryDTO.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.docCategoryDTO.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                  {required: true, message:'备注信息不能为空', trigger:'blur'}
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
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import DocTemplateService from '@/api/wps/DocTemplateService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        pathArra: [],
        inputForm: {
          id: '',
          name: '',
          path: '',
          version: '',
          docCategoryDTO: {
            id: ''
          },
          remarks: ''
        }
      }
    },
    components: {
      SelectTree
    },
    docTemplateService: null,
    created () {
      this.docTemplateService = new DocTemplateService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建文档`
        } else if (method === 'edit') {
          this.title = '修改文档'
        } else if (method === 'view') {
          this.title = '查看文档'
        }
        this.pathArra = []
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.docTemplateService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inputForm.path.split('|').forEach((item) => {
                if (item.trim().length > 0) {
                  this.pathArra.push({name: decodeURIComponent(item.substring(item.lastIndexOf('/') + 1)), url: item})
                }
              })
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
            this.docTemplateService.save(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.loading = false
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>