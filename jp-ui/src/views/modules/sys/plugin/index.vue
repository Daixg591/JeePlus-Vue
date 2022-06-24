<template>
<div class="el-scrollbar__wrap wrap-white">
  <div class="el-scrollbar__view">
    <el-row :gutter="10">
      <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" v-for="(item, index) in dataList" :key="index">
        <el-card  shadow="hover" style="margin: 10px" :body-style="{ padding: '0px' }">
          <div class="jp-card jp-card-bordered">
            <div class="jp-card-cover1"> 
              <i :class="item.icon" style="font-size: 36px"></i> 
              <div class="jp-card-meta-detail"> <font style="font-weight: 700"> {{item.name}}</font> 
              <p style="font-weight: 700">{{item.version}}</p> 
              </div> 
              <el-tooltip placement="top">
                <div slot="content"><font size="4">{{item.name}}(<a :href="item.site" target="blank">访问官网</a>)</font><br/>
                <p style="font-weight: 500" v-html="item.description"></p>
                </div>
                <el-button size="small">详情</el-button>
              </el-tooltip>
            </div>
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
        dataList: []
      }
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.$http({
          url: '/sys/plugin/list',
          method: 'get'
        }).then(({data}) => {
          if (data && data.success) {
            this.dataList = data.rows
            this.loading = false
          }
        })
      }
    }
  }
</script>
<style scoped>
.jp-card-cover1 {
    background-color: #efefef;
    padding-top: 20px;
    padding-left: 0px;
    text-align: center;
    display: block;
    margin: 5px;
    color: rgba(0,0,0,0.5);
}
.jp-card-bordered:hover {
    border: 1px solid #3286ed;
    -moz-box-shadow: 0 0 5px #ccc;
    box-shadow: 5px 5px 10px #ccc;
}
.jp-card-bordered:hover .jp-card-cover1 {
    color: white;
    background: -webkit-linear-gradient(left,#2196f380,#2196f3);
    background: -o-linear-gradient(to left,#2196f380,#2196f3);
    background: -moz-linear-gradient(to left,#2196f380,#2196f3);
    background: linear-gradient(to left,#2196f380,#2196f3);
    background: -ms-linear-gradient(right,#2196f380,#2196f3);
}



</style>
