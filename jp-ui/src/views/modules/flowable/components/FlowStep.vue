<template>
    <el-card class="box-card"  shadow="hover" style="margin-top:5px">
      <div slot="header" class="clearfix">
        <span>流转记录</span>
      </div>
      <el-steps :active="historicTaskStepNodeList.length">
        <el-step :key="index" v-for="(node, index) in historicTaskStepNodeList" 
        :title="node.activityName" finish-status="success" 
         :description="node.assigneeName + node.time"></el-step>
      </el-steps>
      <el-table
      size="small"
      :data="historicTaskList"
      style="width: 100%">
      <el-table-column
        prop="histIns.activityName"
        label="执行环节"
        width="180">
      </el-table-column>
      <el-table-column
        prop="assigneeName"
        label="执行人"
        width="180">
      </el-table-column>
      <el-table-column
        prop="histIns.startTime"
        label="开始时间">
        <template slot-scope="scope">
          {{scope.row.histIns.startTime | formatDate}}
        </template>
      </el-table-column>
       <el-table-column
        prop="histIns.endTime"
        label="结束时间">
        <template slot-scope="scope">
          {{scope.row.histIns.endTime | formatDate}}
        </template>
      </el-table-column>
       <el-table-column
        prop="comment.status"
        label="办理状态">
      </el-table-column>
       <el-table-column
        prop="comment.message"
        label="审批意见">
      </el-table-column>
       <el-table-column
        prop="durationTime"
        label="任务历时">
        <template slot-scope="scope">
          {{scope.row.durationTime || '0秒'}}
        </template>
      </el-table-column>
    </el-table>
    </el-card>
</template>
<script>
export default {
  props: {
    historicTaskList: {
      type: Array,
      default: []
    }
  },
  computed: {
    historicTaskStepNodeList () {
      let nodeArra = []
      this.historicTaskMap.forEach((arra) => {
        let obj = {activityName: '', assigneeName: '', time: ''}
        arra.forEach((node) => {
          obj.activityName = node.histIns.activityName
          obj.assigneeName += (node.assigneeName || '') + ','
          obj.time = (!node.histIns.startTime ? '--' : this.moment(node.histIns.startTime).format('YYYY-MM-DD HH:mm:ss'))
        })
        nodeArra.push(obj)
      })
      return nodeArra
    },
    historicTaskMap () {
      let map = new Map()
      this.historicTaskList.forEach((act) => {
        let key = act.histIns.activityId + parseInt(act.histIns.startTime / 1000)
        let val = map.get(key)
        if (val) {
          val.push(act)
        } else {
          var array = []
          array.push(act)
          map.set(key, array)
        }
      })
      return map
    }
  }
}
</script>