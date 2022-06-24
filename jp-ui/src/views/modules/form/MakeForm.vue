<template>
<div>
  <el-dialog
    :title="title"
    fullscreen
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'"
             label-width="120px">
           <fm-making-form :bindDataTable="inputForm.autoCreate !=='1'" ref="formDesign" style="height:700px" :data="options" v-if="visible"
             :uploadPath ="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=/formbuilder`"
             preview :ds="inputForm.dataSource" :tableName="inputForm.tableName" tab-list generate-json clearable>
             
          </fm-making-form>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'"  @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>


<script>
  import MakeFormService from '@/api/form/MakeFormService'
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
          code: '',
          autoCreate: '1',
          dataSource: {
            id: 'master',
            name: '本地数据库',
            enName: 'master',
            type: ''
          },
          name: '',
          tableName: '',
          source: '',
          version: '',
          remarks: ''
        }
      }
    },
    components: {
    },
    makeFormService: null,
    created () {
      this.makeFormService = new MakeFormService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建数据表单`
        } else if (method === 'edit') {
          this.title = '修改数据表单'
        } else if (method === 'view') {
          this.title = '查看数据表单'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          if (method === 'add') {
            // this.$refs.formDesign.setJSON({'list': [], 'config': {'labelWidth': 100, 'labelPosition': 'right', 'size': 'small', 'customClass': ''}})
          }
          // this.options = {}
          this.inputForm.name = ''
          this.inputForm.tableName = ''
          this.inputForm.source = ''
          this.inputForm.version = ''
          this.inputForm.remarks = ''
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.makeFormService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              if (this.inputForm.source) {
                this.options = JSON.parse(this.inputForm.source)
                this.$refs.formDesign.setJSON(this.options)
              } else {
                // this.$refs.formDesign.setJSON({'list': [], 'config': {'labelWidth': 100, 'labelPosition': 'right', 'size': 'small', 'customClass': ''}})
              }
              this.loading = false
            })
          }
        })
      },
      handleSubmit () {

      },
      // 表单提交
      doSubmit () {
        this.inputForm.source = JSON.stringify(this.$refs.formDesign.getJSON())
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.makeFormService.saveFormSource(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>