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
            <el-form-item label="地图名称" prop="name"
                :rules="[
                  {required: true, message:'地图名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写地图名称"     ></el-input>
           </el-form-item>
        </el-col>
         <el-col :span="12">
            <el-form-item label="地图说明" prop="remarks"
                :rules="[
                 ]">
          <el-input  v-model="inputForm.remarks" placeholder="请填写说明信息"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="地图数据" prop="data"
                :rules="[
                  {required: true, message:'地图数据不能为空', trigger:'blur'}
                 ]">
          <el-input type="textarea"  :autosize="{ minRows: 10, maxRows: 10}" v-model="inputForm.data" placeholder="请填写地图数据"     ></el-input>
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
  import DataMapService from '@/api/datav/DataMapService'
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
          data: '',
          remarks: ''
        }
      }
    },
    components: {
    },
    dataMapService: null,
    created () {
      this.dataMapService = new DataMapService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建地图`
        } else if (method === 'edit') {
          this.title = '修改地图'
        } else if (method === 'view') {
          this.title = '查看地图'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.dataMapService.queryById(this.inputForm.id).then(({data}) => {
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
            this.dataMapService.save(this.inputForm).then(({data}) => {
              this.loading = false
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