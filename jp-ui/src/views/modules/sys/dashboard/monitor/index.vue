<template>
    <div class="el-scrollbar__wrap">
    <div class="el-scrollbar__view">
    <el-row :gutter="10" style="margin-bottom: 10px">
      <el-col
        :lg="18" :md="24" :sm="24" :xs="24"
      >
        <el-card
          header="活动实时交易情况"
        >
          <el-row>
            <el-col :sm="6" :xs="24" >
              <number-info
                sub-title="今日交易总额"
                suffix="元"
                :total="numeral(124543233).format('0,0')"
              />
            </el-col>
            <el-col :sm="6" :xs="24">
              <number-info
                sub-title="销售目标完成率"
                total="92%"
              />
            </el-col>
            <el-col :sm="6" :xs="24">
              <number-info
                sub-title="活动剩余时间"
              >
                <count-down
                  slot="total"
                  :target="targetTime"
                />
              </number-info>
            </el-col>
            <el-col :sm="6" :xs="24">
              <number-info
                sub-title="每秒交易总额"
                suffix="元"
                :total="numeral(234).format('0,0')"
              />
            </el-col>
          </el-row>
          <div class="map-chart">
            <el-tooltip content="等待后期实现" placement="top">
              <img
                src="https://gw.alipayobjects.com/zos/rmsportal/HBWnDEUXCnGnGrRfrpKa.png"
                alt="map"
              />
            </el-tooltip>
          </div>
        </el-card>
      </el-col>
      <el-col :lg="6" :md="24" :sm="24" :xs="24">
        <el-card
          header="活动情况预测"
          style="margin-bottom: 10px"
        >
          <active-chart />
        </el-card>
        <el-card
          header="券核效率"
        >

        <ve-gauge 
          height="200px"
          :series=" [
          {
            type: 'gauge',
            itemStyle: {
              color:  '#2F9CFF'
            },
            splitNumber: 5,
            radius: '85%',
            axisLine: {
              lineStyle: {
                color: [[0, '#2F9CFF'], [80 * 0.01,  '#2F9CFF'], [1,'#F0F2F5']],
                width: 10
              }
            },
            splitLine: {
              length: 10
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              color: 'rgba(0, 0, 0, 0.65)',
              distance: 8
            },
            pointer: {
              length: '70%',
              width: 5
            },
            title: {
              offsetCenter: [0, '70%'],
              color: 'rgba(0, 0, 0, 0.43)',
              fontSize: 14
            },
            silent: true,
            data: [
              {
                value: 80,
                name: '跳出率'
              }
            ]
          }
        ] ">
        </ve-gauge>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-bottom: 10px">
      <el-col :lg="9" :md="24" :sm="24" :xs="24">
        <el-card
          header="各品类占比"
          class="pie-card"
        >
          <el-row>
            <el-col :span="8">
              <el-progress type="circle" :percentage="28">中式快餐</el-progress>
            </el-col>
            <el-col :span="8">
               <el-progress type="circle" :percentage="22">西餐</el-progress>
            </el-col>
            <el-col :span="8">
              <el-progress type="circle" :percentage="32">火锅</el-progress>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :lg="9" :md="12" :sm="24" :xs="24">
        <el-card
          header="热门搜索"
        >
         <ve-wordcloud height="128px" :data="{
          columns: ['word', 'count'],
          rows: [{
            'word': 'addidas',
            'count': 2199
          }, {
            'word': 'textStyle',
            'count': 3229
          }, {
            'word': 'markPoint',
            'count': 1857
          }, {
            'word': 'pie',
            'count': 3892
          }, {
            'word': 'roseType',
            'count': 969
          }, {
            'word': 'label',
            'count': 3751
          }, {
            'word': 'emphasis',
            'count': 1205
          }, {
            'word': 'yAxis',
            'count': 7299
          }, {
            'word': 'name',
            'count': 5418
          }, {
            'word': 'type',
            'count': 2290
          }, {
            'word': 'gridIndex',
            'count': 5146
          }, {
            'word': 'normal',
            'count': 4948
          }, {
            'word': 'itemStyle',
            'count': 3837
          }, {
            'word': 'min',
            'count': 4500
          }, {
            'word': 'silent',
            'count': 5744
          }, {
            'word': 'animation',
            'count': 4840
          }, {
            'word': 'parallelAxis',
            'count': 4029
          }]
        }" :settings="{
              shape: 'diamond'
            }"></ve-wordcloud>
        </el-card>
      </el-col>
      <el-col :lg="6" :md="12" :sm="24" :xs="24">
        <el-card
          header="资源剩余"
        >
          <ve-liquidfill height="126px"  :series="[
          {
            type: 'liquidFill',
            name: '补贴资金剩余',
            radius: '98%',
            data: [34 / 100],
            color: ['#1890FF'],
            outline: {
              borderDistance: 0,
              itemStyle: {
                borderWidth: 2,
                borderColor: '#1890ff'
              }
            },
            backgroundStyle: {
              color: '#fff',
              borderColor: '#fff',
              borderWidth: 1
            },
            itemStyle: {
              opacity: 0.8,
              shadowBlur: 0
            },
            label: {
              show: true,
              color: 'rgba(0, 0, 0, 0.43)',
              fontSize: 28
            }
          }
        ]" ></ve-liquidfill>
        </el-card>
      </el-col>
    </el-row>
  </div>
    </div>
</template>

<script>
import Vue from 'vue'
import * as numeral from 'numeral'
import NumberInfo from '@/components/numberInfo/NumberInfo'
import ActiveChart from './ActiveChart'
import CountDown from '@/components/countDown'
const yuan = (val) => `&yen; ${numeral(val).format('0,0')}`
const defaultFormatter = (val) => {
  switch (val) {
    case 20:
      return '差'
    case 40:
      return '中'
    case 60:
      return '良'
    case 80:
      return '优'
    default:
      return ''
  }
}

export default Vue.extend({
  data () {
    return {
      targetTime: new Date().getTime() + 3900000
    }
  },
  components: {
    NumberInfo,
    CountDown,
    ActiveChart
  },
  computed: {
    tags () {
      return {
        'list|100': [
          {
            name: '@city',
            'value|1-100': 150,
            'type|0-2': 1
          }
        ]
      }
    }
  },
  mounted () {

  },
  methods: {
    yuan (value) {
      return yuan(value)
    },
    numeral (value) {
      return numeral(value)
    },
    defaultFormatter (value) {
      return defaultFormatter(value)
    }
  }
})
</script>

<style lang="scss" scoped>
@import '@/assets/scss/theme.scss';

.map-chart {
  padding-top: 24px;
  height: 457px;
  text-align: center;
  img {
    display: inline-block;
    max-width: 100%;
    max-height: 437px;
  }
}

.pie-card {
 .pie-stat {
    font-size: 24px;
  }
}

@media screen and (max-width: $screen-lg) {
  .map-chart {
    height: auto;
  }
}
</style>
