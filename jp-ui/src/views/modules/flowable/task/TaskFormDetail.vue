<template>
<div>
  <h4 style="text-align:center">{{title}}</h4>
  <img  v-if="code === 1" class="process-status-img" src="../../../../assets/img/flowable/1.png"/>
  <img  v-if="code === 2" class="process-status-img" src="../../../../assets/img/flowable/2.png"/>
  <img  v-if="code === 3" class="process-status-img" src="../../../../assets/img/flowable/3.png"/>
  <img  v-if="code === 4" class="process-status-img" src="../../../../assets/img/flowable/4.png"/>
  <img  v-if="code === 5" class="process-status-img" src="../../../../assets/img/flowable/5.png"/>
  <img  v-if="code === 6" class="process-status-img" src="../../../../assets/img/flowable/6.png"/>
  <el-tabs type="border-card" v-model="selectedTab">
    <el-tab-pane label="表单信息" name="form-first">

      <component :formReadOnly="formReadOnly" v-if="formType === '2'"  :class="formReadOnly?'readonly':''" ref="form" :businessId="businessId" :is="form"></component>
      <PreviewForm   v-if="formType !== '2'"  :processDefinitionId="procDefId" :edit="false" :taskFormData="taskFormData" ref="form"/>

    </el-tab-pane>
    <el-tab-pane label="流程信息" v-if="procInsId"  name="form-second">
       <flow-time-line :historicTaskList="historicTaskList"/>
    </el-tab-pane>
    <el-tab-pane label="流程图"  name="form-third">
       <el-card class="box-card"  shadow="hover">
          <div slot="header" class="clearfix">
            <span>流程图</span>
          </div>
          <flow-chart ref="chart1" v-if="procInsId" :processInstanceId="procInsId" />
          <flow-chart ref="chart2" v-if="!procInsId" :processDefId="procDefId" />
        </el-card>
    </el-tab-pane>
    <el-tab-pane label="流转记录" v-if="procInsId" name="form-forth">
          <flow-step :historicTaskList="historicTaskList"/>
     </el-tab-pane>
  </el-tabs>

</div>
</template>

<script>
  // import FlowChart from '../modeler/FlowChart'
import UserSelect from '@/components/userSelect'
import PreviewForm from '@/views/modules/flowable/form/GenerateFlowableForm'
import FlowStep from '@/views/modules/flowable/components/FlowStep'
import FlowTimeLine from '@/views/modules/flowable/components/FlowTimeLine'
import TaskService from '@/api/flowable/TaskService'
import FormService from '@/api/flowable/FormService'
import ProcessService from '@/api/flowable/ProcessService'
const _import = require('@/router/import-' + process.env.NODE_ENV)
  export default {
    taskDefExtensionService: null,
    taskService: null,
    formService: null,
    flowCopyService: null,
    porcessService: null,
    beforeCreate () {
      this.taskService = new TaskService()
      this.formService = new FormService()
      this.porcessService = new ProcessService()
    },
    activated () {
      this.init()
          // 读取流程表单
      if (this.formType === '2') {
        if (this.formUrl === '/404') {
          this.form = null
          this.$message.info('没有关联流程表单!')
        } else {
          this.form = _import(`modules${this.formUrl}`)
        }
      } else {
      // 读取流程表单
        if (this.formUrl === '/404') {
          this.$refs.form.createForm('')
        } else {
          this.$refs.form.createForm(this.formUrl)
        }
        this.formService.getHistoryTaskFormData({ processInstanceId: this.procInsId, procDefId: this.procDefId, taskDefKey: this.taskDefKey }).then(({data}) => {
          this.taskFormData = data
        })
      }

    // 读取流程状态
      this.porcessService.queryProcessStatus(this.procDefId, this.procInsId).then(({data}) => {
        this.code = data
      })
      this.taskService.historicTaskList(this.procInsId).then(({data}) => {
        this.historicTaskList = data
      })
    },
    components: {
      UserSelect,
      PreviewForm,
      FlowStep,
      FlowTimeLine
      // FlowChart
    },
    watch: {
      selectedTab (val) {
        if (val === 'form-third') {
          if (this.procInsId) {
            this.$refs.chart1.init()
          } else {
            this.$refs.chart2.init()
          }
        }
      }
    },
    methods: {
      init () {
        this.selectedTab = 'form-first'
        this.procDefId = this.$route.query.procDefId
        this.procDefKey = this.$route.query.procDefKey
        this.formType = this.$route.query.formType
        this.formUrl = this.$route.query.formUrl
        this.taskId = this.$route.query.taskId
        this.taskDefKey = this.$route.query.taskDefKey
        this.status = this.$route.query.status
        this.title = this.$route.query.formTitle
        this.businessId = this.$route.query.businessId
        this.procInsId = this.$route.query.procInsId
        this.formReadOnly = true
      }
    },
    data () {
      return {
        form: null,
        formType: '',
        formUrl: '',
        selectedTab: 'frist',
        historicTaskList: [],
        procDefId: '',
        code: '1',
        procInsId: '',
        readOnly: false,
        procDefKey: '',
        taskId: '',
        taskFormData: [],
        taskDefKey: '',
        status: '',
        title: '',
        businessId: ''
      }
    }
  }
</script>
<style lang="less">
.process-status-img {
    height: 180px;
    position: absolute;
    z-index: 999;
    top: 1px;
    right: 1px;
}
</style>