<template>
          <el-card class="box-card"  shadow="hover">
          <div slot="header" class="clearfix">
            <span>流程信息</span>
          </div>

          <el-timeline :reverse="true" v-if="historicTaskMap.size">
   
              <el-timeline-item color="#3f9eff" :key="index" v-for="(arra, index) in historicTaskMap"  :timestamp="moment(arra[1][0].histIns.endTime).format('YYYY-MM-DD')" placement="top">
                
                <el-card  class="flow">
                  <h4>{{arra[1][0].histIns.activityName}}</h4>
                  <el-row :gutter="25">
                  <template  v-for="(act, index) in arra[1]" >
                      <el-col  class="tip" style="margin-left:10px" :key="index" :span="11">
                  <div class="item">
                        <span class="label"  >审批人 : </span>
                        <span class="value">{{act.assigneeName}}</span>
                        
                  </div>
                  <div class="item">
                        <span class="label" >办理状态 : </span>
                        <span class="value">
                          <el-tag :type="act.comment.level"   effect="dark" size="small">{{act.comment.status}} </el-tag>
                        </span>
                  </div>
                  <div class="item">
                        <span class="label" >审批意见 : </span>
                             <el-tooltip effect="dark"  :content="act.comment.message"  placement="top-start">
                                <span  class="value">
                                 {{act.comment.message}}
                              </span>
                             </el-tooltip>
                  </div>
                  <div class="item">
                        <span class="label" >开始时间 : </span>
                        <span class="value">
                            {{act.histIns.startTime | formatDate}}
                        </span>
                  </div>
                  <div class="item">
                        <span class="label" >结束时间 : </span>
                        <span class="value">
                           {{act.histIns.endTime | formatDate}}
                        </span>
                  </div>
                  <div class="item">
                        <span class="label" >用时 : </span>
                        <span class="value">{{act.durationTime || '0秒'}}</span>
                  </div>
                    </el-col>
                  </template>
                    </el-row>
                </el-card>
              </el-timeline-item>
          </el-timeline>
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
      // this.historicTaskMap = map
    }
  }
  // created () {
  //   console.log(this.historicTaskMap)
  // }
}
</script>
<style lang="scss">
.flow{
  .item {
      height: 32px;
      line-height: 32px;
      margin-bottom: 8px;
      .label {
          display: inline-block;
          height: 100%;
          width: 70px;
          font-size: 14px;
          color: #5e6d82;

          text-align: end;
          vertical-align: top;
          &::after {
              display: inline-block;
              width: 100%;
              content: '';
              height: 0;
          }
      }
      .value {
          padding-left: 10px;
          font-size: 14px;
          max-width: calc(100% - 90px);
          color: #5e6d82;
          display: inline-block;
          overflow:hidden;
          white-space:nowrap;
          text-overflow: ellipsis
      }
  }
}
</style>