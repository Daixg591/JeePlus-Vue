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
            <el-form-item label="商品名称" prop="name"
                :rules="[
                  {required: true, message:'商品名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写商品名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="所属类型" prop="category.id"
                :rules="[
                  {required: true, message:'所属类型不能为空', trigger:'blur'}
                 ]">
          <GridSelect
            title="选择所属类型"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.category.id"
            :limit="1"
            @getValue='(value) => {inputForm.category.id=value}'
            :columns="[
            {
              prop: 'name',
              label: '名字'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '名字'
            }
            ]"
            dataListUrl="/test/shop/category/list"
            queryEntityUrl="/test/shop/category/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="价格" prop="price"
                :rules="[
                  {required: true, message:'价格不能为空', trigger:'blur'},
                  {validator: validator.isFloatGtZero, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.price" placeholder="请填写价格"     ></el-input>
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
  import GridSelect from '@/components/gridSelect'
  import GoodsService from '@/api/test/shop/GoodsService'
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
          category: {
            id: ''
          },
          price: '',
          remarks: ''
        }
      }
    },
    components: {
      GridSelect
    },
    goodsService: null,
    created () {
      this.goodsService = new GoodsService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建商品`
        } else if (method === 'edit') {
          this.title = '修改商品'
        } else if (method === 'view') {
          this.title = '查看商品'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.goodsService.queryById(this.inputForm.id).then(({data}) => {
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
            this.goodsService.save(this.inputForm).then(({data}) => {
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

  
