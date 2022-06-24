<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" :rules="dataRule" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="80px" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'" @submit.native.prevent>
      <el-row  :gutter="15">
        <el-col :span="12">
          <el-form-item label="上级区域" prop="parent.id">
             <SelectTree 
             ref="parentTree"
             :props="{
                value: 'id',             // ID字段名
                label: 'name',         // 显示名称
                children: 'children'    // 子级字段名
              }"
            v-if="visible"
            :url="`/sys/area/treeData?extId=${inputForm.id}`"
            :value="inputForm.parent.id"
            :clearable="true" 
            :accordion="true"
            @getValue="(value) => {inputForm.parent.id=value}"/>
          </el-form-item>
        </el-col>
         <el-col :span="12">
            <el-form-item label="区域名称" prop="name">
              <el-input maxlength="50" v-model="inputForm.name" placeholder="区域名称"></el-input>
            </el-form-item>
        </el-col>
     
        <el-col :span="12">
          <el-form-item label="区域编码" prop="code">
            <el-input maxlength="50" v-model="inputForm.code" placeholder="区域编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="区域类型" prop="type">
            <el-select v-model="inputForm.type" placeholder="请选择"  style="width: 100%;">
                <el-option
                  v-for="item in this.$dictUtils.getDictList('sys_area_type')"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
           <el-form-item label="排序号" prop="sort">
            <el-input-number  v-model="inputForm.sort" :step="30" style="width:100%"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remarks">
            <el-input maxlength="200" v-model="inputForm.remarks" type="textarea"
                  :rows="2" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" v-if="method != 'view'" type="primary" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import AreaService from '@/api/sys/AreaService'
  export default {
    data () {
      return {
        areaListTreeProps: {
          label: 'name',
          children: 'children'
        },
        visible: false,
        loading: false,
        method: '',
        title: '新增',
        inputForm: {
          id: '',
          name: '',
          parent: {
            id: '',
            name: ''
          },
          sort: '30',
          code: '', // 区域编码
          type: '', // 区域类型（1：公司；2：部门）
          remarks: ''
        },
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '区域类型不能为空', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '区域编码不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    components: {
      SelectTree
    },
    areaService: null,
    created () {
      this.areaService = new AreaService()
    },
    methods: {
      init (method, obj) {
        this.inputForm.id = obj.id
        this.method = method
        if (method === 'add') {
          this.title = '新增区域'
        } else if (method === 'addChild') {
          this.title = '添加下级区域'
        } else if (method === 'edit') {
          this.title = '修改区域'
        } else if (method === 'view') {
          this.title = '查看区域'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs['inputForm'].resetFields()
          this.inputForm.parent.id = obj.parent.id
          this.inputForm.parent.name = obj.parent.name
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.areaService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.areaService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
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