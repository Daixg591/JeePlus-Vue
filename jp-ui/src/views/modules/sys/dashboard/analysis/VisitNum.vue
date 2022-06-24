<template>
    <div class = "cell-num">
        <el-row>
            <el-col :span = "18" style="padding-right:50px">
                <h4 class="title">访问排行榜</h4>
                <ve-histogram ref="chart" height="400px"  :legend-visible="false" 
                                    :data="data" 
                                    :colors="['#3aa1ff']">
                                  </ve-histogram>
            </el-col>
            <el-col :span = "6">
                <h4 class="title">访问排行榜</h4>
                <ul class="list">
                    <li :key="index" v-for="(item, index) in rankList">
                        <span :class="index < 3 ? 'active' : null">{{ index + 1 }}</span>
                        <span>{{ item.name }}</span>
                        <span>{{ item.total }}</span>
                    </li>
                </ul>
            </el-col>
        </el-row>
        <el-row>

        </el-row>
    </div>
</template>

<script>
  export default {
    name: 'CellNum',
    data () {
      const rankList = []
      for (let i = 0; i < 7; i++) {
        rankList.push({
          name: '白鹭岛 ' + (i + 1) + ' 号店',
          total: 1234.56 - i * 100
        })
      }

      return {
        options: {
          cellNum: {}
        },
        data: {
          rows: [],
          columns: []
        },
        rankList
      }
    },
    activated () {
      this.data.columns = ['日期', '访问']
      this.data.rows = []
      for (let i = 0; i < 12; i += 1) {
        this.data.rows.push({'日期': `${i + 1}月`, '访问': Math.floor(Math.random() * 1000) + 200})
      }
    },
    methods: {
      resize () {
        this.$refs.chart.resize()
      },
      initCellNumCharts () {
        let xData = []
        let yData = []
        for (let i = 0; i < 12; i += 1) {
          xData.push(`${i + 1}月`)
          yData.push(Math.floor(Math.random() * 1000) + 200)
        }
        this.options = {
          ...this.options,
          cellNum: {
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow',
                label: {
                  backgroundColor: '#6a7985'
                }
              }
            },
            grid: {
              left: '50',
              right: '50'
            },
            xAxis: [
              {
                type: 'category',
                                // boundaryGap: false,
                axisTick: {
                  show: false
                },
                splitLine: {            // 网格线
                  show: false
                },
                axisLine: {
                  lineStyle: {
                    color: 'rgba(204,204,204,1)'
                  }
                },
                axisLabel: {
                  color: 'black'
                },
                data: xData
              }
            ],
            yAxis: [
              {
                type: 'value',
                axisLine: {       // y轴
                  show: false
                },
                axisTick: {       // y轴刻度线
                  show: false
                },
                splitLine: {     // 网格线
                  'show': true
                },
                axisLabel: {
                  formatter: (value) => {
                    return value
                  }
                }
              }
            ],
            series: [
              {
                type: 'bar',
                barWidth: 50,
                barGap: '50%',
                itemStyle: {
                  normal: {
                    color: (params) => {
                      let colorList = ['#C33531', '#EFE42A', '#64BD3D', '#EE9201', '#29AAE3', '#B74AE5', '#0AAF9F', '#E89589', '#16A085', '#4A235A', '#C39BD3 ', '#F9E79F', '#BA4A00', '#ECF0F1', '#616A6B', '#EAF2F8', '#4A235A', '#3498DB']
                      return colorList[params.dataIndex]
                    }
                  }
                },
                data: yData
              }
            ]
          }
        }
      }
    },
    mounted () {
      this.initCellNumCharts()
    }
  }
</script>

<style scoped lang = "less">
    .cell-num{
        .echarts {
            width: 100%;
            height: 400px;
        }
        .list {
            margin: 25px 0 0;
            padding: 0;
            list-style: none;
            li {
                margin-top: 16px;
                span {
                    color: rgba(0, 0, 0, .65);
                    font-size: 14px;
                    line-height: 22px;

                    &:first-child {
                        background-color: #f5f5f5;
                        border-radius: 20px;
                        display: inline-block;
                        font-size: 12px;
                        font-weight: 600;
                        margin-right: 24px;
                        height: 20px;
                        line-height: 20px;
                        width: 20px;
                        text-align: center;
                    }
                    &.active {
                        background-color: #314659;
                        color: #fff;
                    }
                    &:last-child {
                        float: right;
                    }
                }
            }
        }
    }
</style>