<template>
  <el-dialog
    title="权限设置"
    :close-on-click-modal="false"
     v-dialogDrag
     width="350px"
    :visible.sync="visible">
    <el-form size="small" class="auth" :model="inputForm" status-icon v-loading="loading" ref="inputForm" @keyup.enter.native="inputFormSubmit()"
             @submit.native.prevent>
             <el-tabs type="border-card">
              <el-tab-pane>
                <span slot="label"><i class="fa fa-navicon"></i> 菜单权限</span>
                <el-scrollbar style="height: 450px">
                  <el-tree
                    :data="menuList"
                    :props=" {
                        label: 'name',
                        children: 'children'
                      }"
                    node-key="id"
                    ref="menuListTree"
                    :default-expanded-keys="['1']"
                    :default-checked-keys="menuCheckedKeys"
                    show-checkbox>
                  </el-tree>
                </el-scrollbar>
              </el-tab-pane>
              <el-tab-pane label="数据权限">
                 <span slot="label"><i class="fa fa-database"></i> 数据权限</span>
                    <el-scrollbar style="height: 450px">
                      <el-tree
                        :data="dataRuleList"
                        :props=" {
                          label: 'name',
                          children: 'children'
                        }"
                        node-key="id"
                        ref="dataRuleTree"
                        :default-expanded-keys="['1']"
                        :default-checked-keys="dataRuleCheckedKeys"
                        show-checkbox>
                      </el-tree>
                    </el-scrollbar>
              </el-tab-pane>
            </el-tabs>
    </el-form>
     <span slot="footer" class="dialog-footer">
      <el-button type="primary" icon="el-icon-check" size="small" plain @click="inputFormSubmit()" v-noMoreClick>保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import MenuService from '@/api/sys/MenuService'
  import DataRuleService from '@/api/sys/DataRuleService'
  import RoleService from '@/api/sys/RoleService'
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        title: '',
        menuList: [],
        dataRuleList: [],
        menuCheckedKeys: [],
        dataRuleCheckedKeys: [],
        inputForm: {
          id: '',
          menuIds: '',
          dataRuleIds: ''
        }
      }
    },
    menuService: null,
    dataRuleService: null,
    roleService: null,
    created () {
      this.menuService = new MenuService()
      this.dataRuleService = new DataRuleService()
      this.roleService = new RoleService()
    },
    methods: {
      init (id) {
        this.inputForm.id = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.$refs.menuListTree.setCheckedKeys([])
          this.$refs.dataRuleTree.setCheckedKeys([])
          let p1 = this.menuService.treeData({isShowHide: '1'})
          let p2 = this.dataRuleService.treeData()
          if (this.inputForm.id) {
            this.loading = true
            let p3 = this.roleService.queryById(this.inputForm.id)
            Promise.all([p1, p2, p3]).then((result) => {
              this.menuList = result[0].data
              this.dataRuleList = result[1].data
              let data = result[2].data
              this.loading = false
              this.inputForm = this.recover(this.inputForm, data)
              this.$refs.menuListTree.setCheckedKeys(data.menuIds.split(','))
              this.$refs.dataRuleTree.setCheckedKeys(data.dataRuleIds.split(','))
            })
          } else {
            Promise.all([p1, p2]).then((result) => {
              this.menuList = result[0].data
              this.dataRuleList = result[1].data
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      inputFormSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          this.inputForm.menuIds = this.$refs.menuListTree.getCheckedKeys().concat(this.$refs.menuListTree.getHalfCheckedKeys()).join(',')
          this.inputForm.dataRuleIds = this.$refs.dataRuleTree.getCheckedKeys().concat(this.$refs.dataRuleTree.getHalfCheckedKeys()).join(',')
          if (valid) {
            this.loading = true
            this.roleService.save(this.inputForm).then(({data}) => {
              this.loading = false
              this.$message.success(data)
              this.visible = false
              this.$emit('refreshDataList')
            })
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
 
  .el-tree {
    min-width: 100%;
    display: inline-block !important;
  }
 .auth {
   margin-top: -30px;
   margin-right: -19px;
   margin-bottom: -30px;
   margin-left: -19px;
 }
 
  .el-scrollbar {
    height: 100%;
  }

</style>