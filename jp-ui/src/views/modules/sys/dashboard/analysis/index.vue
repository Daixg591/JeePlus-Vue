<template>
    <div class="el-scrollbar__wrap">
      <div class="el-scrollbar__view">
        <div class="analyze">
            <el-row class="m-3">
                <quota-explain :info="topCard"></quota-explain>
            </el-row>
            <el-row class="m-3">
                <chart-card>
                    <div slot="card-head" class="relative">
                        <el-tabs v-model="tabs.name">
                            <el-tab-pane label="销售额" name="cellNum">
                                <cell-num ref = "cellNum"></cell-num>
                            </el-tab-pane>
                            <el-tab-pane label="访问量" name="visitNum">
                                <visit-num ref = "visitNum"></visit-num>
                            </el-tab-pane>
                        </el-tabs>
                        <div class="flex float-left absolute top-1 right-0">
                          <el-row>
                            <el-col :span="12">
                                <el-row>
                                    <el-button type="text">今日</el-button>
                                    <el-button type="text">本周</el-button>
                                    <el-button type="text">本月</el-button>
                                    <el-button type="text" class="ml-3">本年</el-button>
                                </el-row>
                            </el-col>
                            <el-col :span="12">
                                <el-date-picker
                                        v-model="chart.range"
                                        type="daterange"
                                        range-separator="~"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期">
                                </el-date-picker>
                            </el-col>
                          </el-row>
                        </div>
                    </div>
                </chart-card>
            </el-row>
            <el-row class="m-3">
                        <el-row :gutter="15">
                            <el-col :span="6">
                                <div class="card-panel">
                                    <div class="card-panel-icon-wrapper icon-location">
                                        <i class="el-icon-platform-eleme card-panel-icon"></i>
                                    </div>
                                    <div class="card-panel-description">
                                        <div class="card-panel-text">
                                            访问量
                                        </div>
                                        <count-to :start-val="0" :end-val="info.ipVisit.num"
                                                  :duration="info.ipVisit.duration" class="card-panel-num"/>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6" >
                                <div class="card-panel">
                                    <div class="card-panel-icon-wrapper icon-sell">
                                        <i class="el-icon-shopping-cart-2 card-panel-icon"></i>
                                    </div>
                                    <div class="card-panel-description">
                                        <div class="card-panel-text">
                                            销售量
                                        </div>
                                        <count-to :start-val="0" :end-val="info.timesVisit.num"
                                                  :duration="info.timesVisit.duration" class="card-panel-num"/>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="card-panel">
                                    <div class="card-panel-icon-wrapper icon-visit">
                                        <i class="el-icon-s-order card-panel-icon"></i>
                                    </div>
                                    <div class="card-panel-description">
                                        <div class="card-panel-text">
                                            订单量
                                        </div>
                                        <count-to :start-val="0" :end-val="info.totalVisit.num"
                                                  :duration="info.totalVisit.duration" class="card-panel-num"/>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="card-panel">
                                    <div class="card-panel-icon-wrapper icon-people">
                                        <i class="el-icon-user-solid card-panel-icon"></i>
                                    </div>
                                    <div class="card-panel-description">
                                        <div class="card-panel-text">
                                            新用户
                                        </div>
                                        <count-to :start-val="0" :end-val="info.totalVisit.num"
                                                  :duration="info.totalVisit.duration" class="card-panel-num"/>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
            </el-row>
        </div>
      </div>
    </div>
</template>

<script>
    import CountTo from 'vue-count-to'
    import QuotaExplain from './QuotaExplain'
    import ChartCard from './ChartCard'
    import CellNum from './CellNum'
    import VisitNum from './VisitNum'

    export default {
      name: 'Analysis',
      components: {
        CountTo,
        CellNum,
        VisitNum,
        QuotaExplain,
        ChartCard
      },
      data () {
        const rankList = []
        for (let i = 0; i < 7; i++) {
          rankList.push({
            name: '白鹭岛 ' + (i + 1) + ' 号店',
            total: 1234.56 - i * 100
          })
        }
        return {
          tabs: {
            name: 'cellNum'
          },
          rankList,
          chart: {
            range: ''
          },
          info: {
            ipVisit: {
              num: 2324,
              duration: 2600
            },
            timesVisit: {
              num: 1213,
              duration: 3000
            },
            totalVisit: {
              num: 1234,
              duration: 4000
            }

          },
          topCard: {
            cellNum: {
              num: 126560,
              duration: 3000
            },
            visitNum: {
              num: 8846,
              duration: 3000
            },
            payCount: {
              num: 6560,
              duration: 3000
            },
            operateEffect: {
              num: 78,
              duration: 3000
            }
          },
          data: {
            rows: [],
            columns: []
          },
          series: {
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
            }
          },
          call: ''
        }
      },
      activated () {
        this.data.columns = ['日期', '销量']
        this.data.rows = []
        for (let i = 0; i < 12; i += 1) {
          this.data.rows.push({'日期': `${i + 1}月`, '销量': Math.floor(Math.random() * 1000) + 200})
        }
      },
      watch: {
        'tabs.name' (val) {
          if (val === 'visitNum') {
            this.$nextTick(_ => {
              this.$refs.visitNum.resize()
            })
          }
        }
      },
      methods: {
        initValue () {
          let {ipVisit, timesVisit, totalVisit} = this.info
          this.info = {
            ...this.info,
            ipVisit: {
              ...ipVisit,
              num: 0
            },
            timesVisit: {
              ...timesVisit,
              num: 0
            },
            totalVisit: {
              ...totalVisit,
              num: 0
            }
          }
        },
        initTopCardValue () {
          let {cellNum, visitNum, payCount, operateEffect} = this.topCard
          this.topCard = {
            cellNum: {
              ...cellNum,
              num: 232
            },
            visitNum: {
              ...visitNum,
              num: 123
            },
            payCount: {
              ...payCount,
              num: 3543
            },
            operateEffect: {
              ...operateEffect,
              num: 1234
            }
          }
        },
        getVisitNum () {
          this.$refs.visitNum.initCellNumCharts()
        },
        getCellNum () {
          this.$refs.cellNum.initCellNumCharts()
        },
        getTopCardInfo () {
          this.initTopCardValue()
          this.$nextTick(() => {
            this.topCard = {
              cellNum: {
                num: 126560,
                duration: 3000
              },
              visitNum: {
                num: 8846,
                duration: 3000
              },
              payCount: {
                num: 6560,
                duration: 3000
              },
              operateEffect: {
                num: 78,
                duration: 3000
              }
            }
          })
        },
        getLoginInfo () {
          this.initValue()
        },
        cycleCall () {
          this.call = setInterval(() => {
            this.getTopCardInfo()
            this.getCellNum()
            this.getVisitNum()
          }, 8000)
        },
        stopCall () {
          clearInterval(this.call)
        }
      },
      mounted () {
        this.cycleCall()
        this.getTopCardInfo()
      },
      destroyed () {
        this.stopCall()
      }
    }
</script>

<style scoped lang="less">
    .analyze {
        
        .top-1 {
            top: 0.55rem;
        }
        .mt-0 {
            margin-top: 0;
        }

        .m-3 {
            margin-bottom: 0.75rem;
        }

        .right-0 {
            right: 200px;
        }
        .absolute {
            position: absolute;
        }
        .float-left {
            float: left;
        }
        .flex {
            display: flex;
        }
        .card-panel {
            height: 108px;
            cursor: pointer;
            font-size: 12px;
            position: relative;
            overflow: hidden;
            color: #666;
            background: #ffffff;
            box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
            border-color: rgba(0, 0, 0, .05);
            &:hover {
                .card-panel-icon-wrapper {
                    color: #fff;
                }
                .icon-sell {
                    background: #6778fa;
                }
                .icon-people {
                    background: #40c9c6;
                }
                .icon-location {
                    background: #36a3f7;
                }
                .icon-visit {
                    background: #f4516c;
                }
            }
            .icon-sell {
                color: #6778fa;
            }
            .icon-people {
                color: #40c9c6;
            }
            .icon-location {
                color: #36a3f7;
            }
            .icon-visit {
                color: #f4516c;
            }
            .card-panel-icon-wrapper {
                float: left;
                margin: 14px 0 0 14px;
                padding: 16px;
                transition: all 0.38s ease-out;
                border-radius: 6px;
            }
            .card-panel-icon {
                float: left;
                font-size: 48px;
            }
            .card-panel-description {
                float: right;
                font-weight: bold;
                margin: 26px;
                margin-left: 0px;
                .card-panel-text {
                    line-height: 18px;
                    color: rgba(0, 0, 0, 0.45);
                    font-size: 16px;
                    margin-bottom: 12px;
                }
                .card-panel-num {
                    font-size: 20px;
                }
            }
        }
    }
</style>
