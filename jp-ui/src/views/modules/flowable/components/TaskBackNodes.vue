<template>
    <el-dialog title="退回任务" 
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible" :appendToBody="true">
         <el-select v-model="backTaskDefKey" style="width:100%" placeholder="请选择驳回节点">
          <el-option
            v-for="item in backNodes"
            :key="item.taskDefKey"
            :label="item.taskName"
            :value="item.taskDefKey">
          </el-option>
        </el-select>

        <div slot="footer" class="dialog-footer">
            <el-button icon="el-icon-close" @click="visible = false">取消</el-button>
            <el-button icon="el-icon-check" type="primary" @click="doConfirm">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import TaskService from '@/api/flowable/TaskService'
    export default {
      name: 'TaskBackNodes',
      computed: {
        dialogTaskBackNodesInChild: {
          get () {
            return this.visible
          },
          set (val) {
            this.$emit('update:visible', val)
          }
        }
      },
      data () {
        return {
          visible: false,
          backNodes: [],
          backTaskDefKey: ''
        }
      },
      taskService: null,
      created () {
        this.taskService = new TaskService()
      },
      methods: {
        init (taskId) {
          this.visible = true
          this.taskService.backNodes(taskId).then(({data}) => {
            this.backNodes = data
          })
        },
        doConfirm () {
          this.visible = false
          this.$emit('getBackTaskDefKey', this.backTaskDefKey)
        }
      }
    }
</script>

