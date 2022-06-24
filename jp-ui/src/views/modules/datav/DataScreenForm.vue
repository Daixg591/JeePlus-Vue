<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form  :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="分类" prop="category.id"
                :rules="[
                 ]">
                    <SelectTree
                      ref="category"
                      :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }"

                      url="/datav/dataScreenCategory/treeData"
                      :value="inputForm.category.id"
                      :clearable="true"
                      :accordion="true"
                      @getValue="(value) => {inputForm.category.id=value}"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="名称" prop="name" :rules="[{required: true, message:'名称不能为空', trigger:'blur'}]">
              <el-input v-model="inputForm.name" placeholder="请填写大屏名称" ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24" v-if="method === 'add'">
           <el-form-item label="尺寸">
             <el-row>
               <el-col :span="12">
                 <el-form-item label="宽" prop="width" :rules="[{required: true, message:'宽不能为空', trigger:'blur'}]">
                    <el-input-number v-model="inputForm.width" style="width:200px" size="small" controls-position="right"></el-input-number>
                 </el-form-item>
               </el-col>
           <!-- </el-form-item>
            <el-form-item label="大屏名称" prop="name"> -->
                <el-col :span="12">
                  <el-form-item label="高" prop="height" :rules="[{required: true, message:'高不能为空', trigger:'blur'}]">
                    <el-input-number v-model="inputForm.height" style="width:200px" size="small" controls-position="right" ></el-input-number>
                  </el-form-item>
                </el-col>
             </el-row>
            </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="启用" prop="status"
                :rules="[
                 ]">
                <el-select v-model="inputForm.status" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in $dictUtils.getDictList('yes_no')"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
           </el-form-item>
        </el-col>
        <el-col :span="24">
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
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import DataScreenService from '@/api/datav/DataScreenService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          category: {
            id: ''
          },
          width: 1920,
          height: 1080,
          component: '[]',
          detail: '',
          status: '',
          name: '',
          screenShot: '/img/bg/bg1.png',
          remarks: ''
        }
      }
    },
    components: {
      SelectTree
    },
    dataScreenService: null,
    created () {
      this.dataScreenService = new DataScreenService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建大屏`
          this.inputForm = {
            id: '',
            category: {
              id: ''
            },
            width: 1920,
            height: 1080,
            component: '[]',
            detail: '',
            status: '',
            name: '',
            screenShot: '/img/bg/bg1.png',
            remarks: ''
          }
        } else if (method === 'edit') {
          this.title = '修改大屏'
        } else if (method === 'view') {
          this.title = '查看大屏'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.dataScreenService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        if (this.method === 'add') {
          this.inputForm.detail = `{"name":"${this.inputForm.name}","width":${this.inputForm.width},"height":${this.inputForm.height},"scale":1,"backgroundImage":"/img/bg/bg1.png","url":"","mark":{},"gradeShow":false,"gradeLen":30}`
        }
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.dataScreenService.save(this.inputForm).then(({data}) => {
              this.loading = false
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