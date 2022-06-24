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
            <el-form-item label="名称" prop="name"
                :rules="[
                  {required: true, message:'名称不能为空', trigger:'blur'}
                 ]">
          <el-input v-model="inputForm.name" placeholder="请填写名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="上级类型" prop="parent.id"
                :rules="[
                 ]">
                <SelectTree
                      ref="parent"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"
                      v-if="visible"
                      :url="`/test/treetable/carKind/treeData?extId=${inputForm.id}`"
                      :value="inputForm.parent.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.parent.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="排序" prop="sort"
                :rules="[
                  {required: true, message:'排序不能为空', trigger:'blur'},
                  {validator: validator.isIntGteZero, trigger:'blur'}
                 ]">
          <el-input-number v-model="inputForm.sort" placeholder="请填写排序"     style="width: 100%;"></el-input-number>
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
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import CarKindService from '@/api/test/treetable/CarKindService'
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
          parent: {
            id: ''
          },
          sort: '',
          remarks: ''
        }
      }
    },
    components: {
      SelectTree
    },
    carKindService: null,
    created () {
      this.carKindService = new CarKindService()
    },
    methods: {
      init (method, obj) {
        this.method = method
        this.inputForm.id = obj.id
        if (method === 'add') {
          this.title = '新建车系'
        } else if (method === 'addChild') {
          this.title = '添加下级车系'
        } else if (method === 'edit') {
          this.title = '修改车系'
        } else if (method === 'view') {
          this.title = '查看车系'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.parent.id = obj.parent.id
          this.inputForm.parent.name = obj.parent.name
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.carKindService.queryById(this.inputForm.id).then(({data}) => {
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
            this.carKindService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshTree')
            })
          }
        })
      }
    }
  }
</script>

  
