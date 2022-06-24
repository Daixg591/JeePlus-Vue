<template>
  <div v-loading="loading"> 
    <el-row :gutter="12" v-if="ServerOS" >
      <el-col :span="8">
        <el-card shadow="hover" class="server-card">
            <div slot="header" class="clearfix">
              <span>CPU使用率(监测10秒)</span>
              <el-button @click="refreshList" style="float: right; padding: 3px 0" type="text">刷新</el-button>
            </div>
            <el-row>
              <el-col :span="24" style="text-align:center">
                <el-progress type="dashboard" :percentage="ServerOS.cpu.used" :color="colors"></el-progress>
              </el-col>
              <el-col :span="24">
                  <div class="el-table el-table--enable-row-hover el-table--medium">
                    <table cellspacing="0" style="width: 100%;">
                      <tbody>
                        <tr>
                          <td><div class="cell">CPU主频</div></td>
                          <td><div class="cell">{{ServerOS.cpu.cpucard.processorIdentifier.name.split('@')[1]}}</div></td>
                        </tr>
                        <tr>
                          <td><div class="cell">核心数</div></td>
                          <td><div class="cell">{{ServerOS.cpu.cpucard.logicalProcessorCount}}</div></td>
                        </tr>
                      </tbody>
                    </table>
                </div>
              </el-col>
            </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="server-card" >
            <div slot="header" class="clearfix">
              <span>内存使用率(监测10秒)</span>
              <el-button @click="refreshList" style="float: right; padding: 3px 0" type="text">刷新</el-button>
            </div>
             <el-row>
              <el-col :span="24" style="text-align:center">
              <el-progress type="dashboard" :percentage="ServerOS.mem.used" :color="colors"></el-progress>
              </el-col>
              <el-col :span="24">
               <div class="el-table el-table--enable-row-hover el-table--medium">
                    <table cellspacing="0" style="width: 100%;">
                      <tbody>
                        <tr>
                          <td><div class="cell">总内存</div></td>
                          <td><div class="cell">{{ServerOS.mem.total}}G</div></td>
                        </tr>
                        <tr>
                          <td><div class="cell">已用内存</div></td>
                          <td><div class="cell">{{ServerOS.mem.usedMem}}G</div></td>
                        </tr>
                      </tbody>
                    </table>
                </div>
                 </el-col>
            </el-row>
    
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="server-card">
            <div slot="header" class="clearfix">
              <span>JVM使用率(监测10秒)</span>
              <el-button @click="refreshList" style="float: right; padding: 3px 0" type="text">刷新</el-button>
            </div>
             <el-row>
              <el-col :span="24" style="text-align:center">
                  <el-progress type="dashboard" :percentage="ServerOS.jvm.used" :color="colors"></el-progress>
              </el-col>
              <el-col :span="24">
                  <div class="el-table el-table--enable-row-hover el-table--medium">
                    <table cellspacing="0" style="width: 100%;">
                      <tbody>
                        <tr>
                          <td><div class="cell">JVM大小</div></td>
                          <td><div class="cell">{{ServerOS.jvm.total}}M</div></td>
                        </tr>
                        <tr>
                          <td><div class="cell">已用JVM</div></td>
                          <td><div class="cell">{{ServerOS.jvm.usedMem}}M</div></td>
                        </tr>
                      </tbody>
                    </table>
                </div>
              </el-col>
            </el-row>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" class="server-card">
            <div slot="header" class="clearfix">
              <span>JAVA虚拟机信息</span>
            </div>
            <div class="el-table el-table--enable-row-hover el-table--medium">
              <table v-if="ServerOS" cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">JAVA名称</div></td>
                  <td><div class="cell">{{ServerOS.sys['java.vm.name']}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">JAVA版本</div></td>
                  <td><div class="cell">{{ServerOS.sys['java.version']}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Java的安装路径	</div></td>
                  <td><div class="cell">{{ServerOS.sys['java.home']}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Java供应商</div></td>
                  <td><div class="cell">{{ServerOS.sys['java.vendor']}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Jvm最大可用内存</div></td>
                  <td><div class="cell">{{ServerOS.jvm.maxTotal}}M</div></td>
                </tr>
              </tbody>
            </table>
            </div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" class="server-card">
            <div slot="header" class="clearfix">
              <span>磁盘信息</span>
            </div>
            <div class="el-table el-table--enable-row-hover el-table--medium">
              <table v-if="ServerOS" cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">磁盘名称</div></td>
                  <td><div class="cell">大小</div></td>
                  <td><div class="cell">已用</div></td>
                  <td><div class="cell">可用</div></td>
                  <td><div class="cell">利用率</div></td>
                </tr>
                <tr v-for="(disk, index) in ServerOS.file" :key="index">
                  <td><div class="cell">{{disk.name}}</div></td>
                  <td><div class="cell">{{disk.total}}G</div></td>
                  <td><div class="cell">{{disk.used}}G</div></td>
                  <td><div class="cell">{{disk.free}}G</div></td>
                  <td><div class="cell"><el-progress :percentage="disk.rate"></el-progress></div></td>
                </tr>
              </tbody>
            </table>
            </div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" class="server-card">
            <div slot="header" class="clearfix">
              <span>服务器信息</span>
            </div>
             <div class="el-table el-table--enable-row-hover el-table--medium">
            <table v-if="ServerOS" cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">服务器名称</div></td>
                  <td><div class="cell">{{ServerOS.hostname}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">操作系统</div></td>
                  <td><div class="cell">{{ServerOS.sys['os.name']}}{{ServerOS.sys['os.version']}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">服务器IP</div></td>
                  <td><div class="cell">{{ServerOS.ip}}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">系统架构</div></td>
                  <td><div class="cell">{{ServerOS.sys['os.arch']}}</div></td>
                </tr>
                 <tr>
                  <td><div class="cell">CPU</div></td>
                  <td><div class="cell">{{ServerOS.cpu.cpucard.name}}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        loading: false,
        interval: '',
        colors: [
          {color: '#0cc26c', percentage: 20},
          {color: '#0cc26c', percentage: 40},
          {color: '#eb6607', percentage: 60},
          {color: '#f5212d', percentage: 80},
          {color: 'red', percentage: 100}
        ],
        ServerOS: undefined
      }
    },
    components: {

    },
    activated () {
      this.refreshList()
    },
    destroyed () {
      clearInterval(this.interval)
    },
    methods: {
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.getInfo()
        var timesRun = 0
        if (this.interval) {
          clearInterval(this.interval)
        }
        this.interval = setInterval(() => {
          timesRun += 1
          if (timesRun === 10) {
            clearInterval(this.interval)
          }
          this.getInfo()
        }, 2000)
      },
      getInfo () {
        this.$http({
          url: '/monitor/server/info',
          method: 'get'
        }).then(({data}) => {
          this.ServerOS = data
          this.loading = false
        })
      }
    }
  }
</script>
<style>
  .server-card {
    margin-bottom: 18px;
    /* padding-bottom: 20px; */
  }
</style>