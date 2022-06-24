<template>
    <div class="el-scrollbar__wrap wrap-white">
      <div class="el-scrollbar__view">
        <el-row :gutter="0">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" v-for="item in dataList" :key="item.id">
            <el-card style="margin: 10px" :body-style="{ padding: '0px' }" class="jp-card jp-card-bordered" shadow="always" >
                <div class="">
                    <div class="jp-card-cover1">
                        <i :class="item.icon" style="font-size: 64px;color:#409EFF;margin-right:20px"></i>
                        <font style="font-weight: 700"> {{item.name}}</font>
                    </div>

                    <div class="jp-card-body">
                        <div class="jp-card-meta">
                            <div class="jp-card-meta-detail">
                                <div class="jp-card-meta-description">{{item.remarks}}</div>
                            </div>
                        </div>
                    </div>
                    <ul class="jp-card-actions">
                        <li style="width: 100%">
                        <el-tooltip :content="item.tip" placement="top">
                            <router-link :to="item.path" :disabled="item.disabled">
                                <i class="fa fa-folder-open-o" :style="`${item.disabled?'color:grey':''}`"></i>
                            </router-link>
                        </el-tooltip>
                        </li>
                    </ul>
                </div>
            </el-card>
        </el-col>
        </el-row>
      </div>
    </div>
</template>

<script>
  export default {
    data () {
      return {
        dataList: [
          {
            name: '代码生成',
            tip: '打开代码生成器',
            remarks: '通过设置生成增删改查功能。',
            icon: 'fa fa-code',
            disabled: false,
            path: '/gen/GenTableList'
          },
          {
            name: '图表开发',
            tip: '可视化配置图表',
            remarks: '快速可视化开发图表',
            icon: 'fa fa-area-chart',
            disabled: false,
            path: '/echarts/EchartsList'

          },
          {
            name: '报表开发',
            tip: '打开报表设计器',
            remarks: '通过设置快速开发报表。',
            icon: 'fa fa-file-excel-o',
            disabled: false,
            path: '/reports/index'

          },
          {
            name: '表单开发',
            tip: '打开表单设计器',
            remarks: '通过设置快速开发自定义表单。',
            icon: 'fa fa-wpforms',
            disabled: false,
            path: '/form/MakeFormList'
          },
          {
            name: '仪表盘开发',
            tip: '即将上线，敬请期待',
            remarks: '通过设置快速开发仪表盘。',
            icon: 'fa fa-dashboard',
            disabled: true,
            path: ''
          },
          {
            name: '数据管理',
            tip: '打开数据管理',
            remarks: '通过sql语句快速创建模型。',
            icon: 'fa fa-database',
            disabled: false,
            path: '/database/datamodel/DataSetList'
          }
        ]
      }
    },
    methods: {
      // 新增
      add () {
        this.$refs.templateForm.init('add', '')
      }
    }
  }
</script>

