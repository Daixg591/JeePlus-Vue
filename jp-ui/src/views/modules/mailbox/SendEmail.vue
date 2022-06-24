<template>
  <el-dialog
    title="写站内信"
    width="1200px"
    center
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="16">
            <el-form-item label="发送到" prop="receiverIds"
                :rules="[
                  {required: true, message:'收信人不能为空', trigger:'blur'}
                 ]">
          		   <user-select  :value="inputForm.receiverIds" @getValue='(value) => {inputForm.receiverIds=value}'></user-select>
           </el-form-item>
        </el-col>
        <el-col :span="16">
            <el-form-item label="标题" prop="mailDTO.title"
                :rules="[
                 ]">
			        <el-input v-model="inputForm.mailDTO.title" placeholder="请输入标题"     ></el-input>
	         </el-form-item>
        </el-col>
         <el-col :span="24">
           <el-form-item label="内容">
            <WangeEditor ref="editor"    v-model="inputForm.mailDTO.content"/>
           </el-form-item>
         </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="danger" @click="doSubmit('0')">存为草稿</el-button>
      <el-button size="small"  type="primary" @click="doSubmit('1')">发送邮件</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import UserSelect from '@/components/userSelect'
  import WangeEditor from '@/components/editor/WangEditor'
  import MailComposeService from '@/api/mail/MailComposeService'
  export default {
    data () {
      return {
        visible: false,
        inputForm: {
          id: '',
          status: '',
          receiverIds: '',
          mailDTO: {
            title: '',
            overview: '',
            content: ''
          }
        }
      }
    },
    components: {
      UserSelect,
      WangeEditor
    },
    mailComposeService: null,
    created () {
      this.mailComposeService = new MailComposeService()
    },
    methods: {
      init (method, obj) {
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.mailDTO.content = ''
          this.$refs.editor.init(this.inputForm.mailDTO.content)
          if (method === 'add') {
            this.inputForm.id = ''
          } else if (method === 'edit') { // 修改
            this.inputForm.id = obj
            this.mailComposeService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.$refs.editor.init(this.inputForm.mailDTO.content)
            })
          } else if (method === 'reply') { // 修改
            this.inputForm.mailDTO.title = obj.title
            this.inputForm.mailDTO.content = obj.content
            this.$refs.editor.init(this.inputForm.mailDTO.content)
            this.inputForm.receiverIds = obj.sender.id
          }
        })
      },
            // 表单提交
      doSubmit (status) {
        this.inputForm.status = status
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.mailComposeService.save(this.inputForm).then(({data}) => {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
              this.$emit('refreshList')
            })
          }
        })
      }
    }
  }
</script>