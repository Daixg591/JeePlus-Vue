<template>
<div>
  <el-dialog
    :title="title+typeList[inputForm.type]"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" v-loading="loading" :class="method==='view'?'readonly':''" :disabled="method==='view'" :rules="dataRule" ref="inputForm" @keyup.enter.native="doSubmit()"
             label-width="100px" @submit.native.prevent>
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="inputForm.type">
              <el-radio v-for="(type, index) in typeList" :label="index.toString()" :key="index">{{ type }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="上级菜单" prop="parent.id">
             <SelectTree 
             ref="menuParentTree"
             :props="{
                value: 'id',             // ID字段名
                label: 'name',         // 显示名称
                children: 'children'    // 子级字段名
              }"
            :data="menuList"
            :value="inputForm.parent.id"
            :clearable="true" 
            :accordion="true"
            @getValue="(value) => {inputForm.parent.id=value}"/>
          </el-form-item>
           <el-form-item :label="typeList[inputForm.type] + '名称'" prop="name">
            <el-input maxlength="50" v-model="inputForm.name" :placeholder="typeList[inputForm.type] + '名称'"></el-input>
          </el-form-item>
          <el-form-item v-if="inputForm.type === '1' || inputForm.type === '2' || inputForm.type === '3'" label="链接地址" prop="href">
              <el-input maxlength="1000" v-model="inputForm.href" placeholder="请填写路由路径或者超链接"></el-input>
          </el-form-item>
          <el-form-item v-if="inputForm.type === '1' || inputForm.type === '2' || inputForm.type === '3'" label="链接类型" prop="target">
            <el-select v-model="inputForm.target" placeholder="如果是路由路径请留空白，http链接或者外部链接请选择iframe"  clearable style="width: 100%;">
                <el-option
                  v-for="item in [{label: 'iframe', value: 'iframe'}]"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="inputForm.type !== '2'  && inputForm.type !== '3'" label="菜单图标" prop="icon">
                <el-input v-model="inputForm.icon" clearable @focus="selectIcon" placeholder="菜单图标名称"></el-input>
          </el-form-item>
          <el-form-item v-if="inputForm.type !== '2' && inputForm.type !== '3'"  label="可见" prop="isShow">
            <el-radio-group v-model="inputForm.isShow">
              <el-radio v-for="item in this.$dictUtils.getDictList('show_hide')" :label="item.value" :key="item.id">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
         <el-form-item v-if="inputForm.type == '1'"  label="固定在标签栏" prop="affix">
            <el-radio-group v-model="inputForm.affix">
              <el-radio label="1">是</el-radio>
              <el-radio label="2">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="inputForm.type !== '0' && inputForm.type !== '3'" label="授权标识" prop="permission">
            <el-input v-model="inputForm.permission" maxlength="50" placeholder="多个用逗号分隔, 如: user:list,user:create"></el-input>
          </el-form-item>
           <el-form-item  label="排序号" prop="sort">
              <el-input-number v-model="inputForm.sort" :step="30" controls-position="right" :min="0" label="排序号"></el-input-number>
           </el-form-item>
          <el-form-item label="备注" prop="remarks">
            <el-input v-model="inputForm.remarks" maxlength="200" type="textarea"
                  :rows="2" placeholder="备注"></el-input>
          </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false" icon="el-icon-circle-close">关闭</el-button>
      <el-button size="small" v-if="method != 'view'" type="primary" @click="doSubmit()" icon="el-icon-circle-check" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
  <Icon ref="icon" @getValue="value => inputForm.icon = value"></Icon>
</div>
</template>

<script>
  import Icon from '@/components/icon'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import MenuService from '@/api/sys/MenuService'
  export default {
    data () {
      const validateUrl = (rule, value, callback) => {
        if (this.inputForm.type === 1 && !/\S/.test(value)) {
          callback(new Error('菜单URL不能为空'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        loading: false,
        method: '',
        title: '新增',
        typeList: ['目录', '菜单', '按钮', '路由'],
        menuList: [],
        menuListTreeProps: {
          label: 'name',
          children: 'children'
        },
        inputForm: {
          id: '',
          type: '1',
          name: '',
          parent: {
            id: '',
            name: ''
          },
          href: '',
          permission: '',
          sort: 30,
          icon: '',
          remarks: '',
          target: '',
          isShow: '1',
          affix: '2'
        },
        dataRule: {
          name: [
            {required: true, message: '菜单名称不能为空', trigger: 'blur'}
          ],
          'parent.id': [
            {required: true, message: '上级菜单不能为空', trigger: 'change'}
          ],
          url: [
            {validator: validateUrl, trigger: 'blur'}
          ]
        }
      }
    },
    components: {
      SelectTree,
      Icon
    },
    menuService: null,
    created () {
      this.menuService = new MenuService()
    },
    methods: {
      init (method, obj) {
        this.method = method
        this.inputForm.id = obj.id
        if (method === 'add') {
          this.title = `新增`
        } else if (method === 'addChild') {
          this.title = '添加下级'
        } else if (method === 'edit') {
          this.title = '修改'
        } else if (method === 'view') {
          this.title = '查看'
        }
        this.menuService.treeData({extId: this.inputForm.id}).then(({data}) => {
          this.menuList = data
          this.inputForm.parent.id = ''
          this.inputForm.parent.name = ''
        }).then(() => {
          this.visible = true
          this.$nextTick(() => {
            this.$refs.menuParentTree.clearHandle()
            this.$refs['inputForm'].resetFields()
            this.inputForm.parent.id = obj.parent.id
            this.inputForm.parent.name = obj.parent.name
          })
        }).then(() => {
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.menuService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
            })
          }
        })
      },
      selectIcon () {
        this.$refs.icon.visible = true
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            if (this.inputForm.type === '0') {
              this.inputForm.href = ''
            }
            if (this.inputForm.type === '2' || this.inputForm.type === '3') {
              this.inputForm.isShow = '0'
            }
            this.menuService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message.success(data)
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
<style>

</style>
