<template>
<div>
  <el-dialog
  fullscreen
    title="设计流程表单"
    center
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
        <fm-making-form ref="formDesign" style="height:700px" :data="options"  v-if="visible"
          :uploadPath ="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=/formbuilder`"
          preview clearable>
              <template slot="action">
              </template>
        </fm-making-form>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" v-if="method != 'view'" @click="doSubmit(0)" v-noMoreClick>保存草稿</el-button>
      <el-button type="primary" v-if="method != 'view'" @click="doSubmit(1)" v-noMoreClick>保存并发布</el-button>
      <el-button @click="visible = false">放弃</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import FormDefinitionJsonService from '@/api/flowable/FormDefinitionJsonService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        options: {},
        inputForm: {
          id: '',
          version: '',
          formDefinitionId: '',
          status: '',
          isPrimary: '',
          json: ''
        }
      }
    },
    formDefinitionJsonService: null,
    created () {
      this.formDefinitionJsonService = new FormDefinitionJsonService()
    },
    methods: {
      init (formDefinitionId, jsonId) {
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.inputForm.json = ''
          this.inputForm.version = ''
          this.inputForm.id = jsonId
          this.inputForm.formDefinitionId = formDefinitionId
          if (jsonId) { // 修改或者查看
            this.loading = true
            this.formDefinitionJsonService.queryById(jsonId).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              if (this.inputForm.json) {
                this.options = JSON.parse(this.inputForm.json)
                this.$refs.formDesign.setJSON(this.options)
              }
              this.loading = false
            })
          } else {
            this.$refs.formDesign.setJSON({'list': [], 'config': {'labelWidth': 100, 'labelPosition': 'right', 'size': 'small', 'customClass': ''}})
          }
        })
      },
      // 表单提交
      doSubmit (status) {
        this.inputForm.json = JSON.stringify(this.$refs.formDesign.getJSON())
        this.inputForm.status = status
        this.inputForm.isPrimary = '1'
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.formDefinitionJsonService.save(this.inputForm).then(({data}) => {
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