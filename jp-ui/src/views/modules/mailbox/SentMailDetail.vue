<template>
  <el-dialog
    :title="inputForm.mailDTO.title"
    width="70%"
    center
    :close-on-click-modal="false"
    :before-close="handleClose"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm">
          <div>
              <span>发件人：本人</span>
              <el-divider></el-divider>
              <span>收件人：{{inputForm.receiverNames}}</span>
              <el-divider></el-divider>
              <span>时间：{{inputForm.sendTime}}</span>
              <el-divider></el-divider>
               <el-card class="box-card">
                 <p v-html="$utils.unescapeHTML(inputForm.mailDTO.content)"></p>
            </el-card>
            </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
       <el-button type="danger" @click="handleClose()" icon="el-icon-circle-close">关闭</el-button>
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
        method: '',
        content: '',
        editorOption: {},
        visible: false,
        status: '',
        inputForm: {
          id: '',
          status: '',
          receiverIds: '',
          receiverNames: '',
          sendTime: '',
          mailDTO: {
            title: '',
            overview: '',
            content: ''
          },
          sender: {
            name: ''
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
      init (id) {
        this.inputForm.id = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          this.mailComposeService.queryById(this.inputForm.id).then(({data}) => {
            this.inputForm = this.recover(this.inputForm, data)
          })
        })
      },
            // 表单提交
      handleClose () {
        this.visible = false
        this.$emit('refreshList')
      }
    }
  }
</script>