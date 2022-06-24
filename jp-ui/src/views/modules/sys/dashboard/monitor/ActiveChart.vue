<template>
  <div class="active-chart">
    <number-info
      sub-title="目标评估"
      total="有望达到预期"
    />
    <div :style="{marginTop: '32px'}">
    <ve-line  height="84px"  
     :tooltip="options.tooltip" 
      :grid="options.grid" 
      :xAxis="options.xAxis" 
      :yAxis="options.yAxis" 
      :series="options.series" 
    :data="activeData"></ve-line>
    </div>
    <template v-if="activeData">
      <div class="active-chart-grid">
        <p>{{[...activeData].sort()[activeData.length - 1].y + 200}} 亿元</p>
        <p>{{[...activeData].sort()[Math.floor(activeData.length / 2)].y}} 亿元</p>
      </div>
    </template>
    <template v-if="activeData">
      <div class="active-chart-legend">
        <span>00:00</span>
        <span>{{activeData[Math.floor(activeData.length / 2)].x}}</span>
        <span>{{activeData[activeData.length - 1].x}}</span>
      </div>
    </template>
  </div>
</template>

<script>
import NumberInfo from '@/components/numberInfo/NumberInfo'

function fixedZero (val) {
  return val * 1 < 10 ? `0${val}` : val
}

function getActiveData () {
  const activeData = []
  for (let i = 0; i < 24; i += 1) {
    activeData.push({
      x: `${fixedZero(i)}:00`,
      y: Math.floor(Math.random() * 200) + i * 50
    })
  }
  return activeData
}
function colorSpan (color) {
  return `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:8px;height:8px;background-color:${color};border: 1px solid #fff;"></span>`
}

export default {
  components: {
    NumberInfo
  },
  data () {
    const timer = null
    return {
      activeData: getActiveData(),
      timer,
      color: 'rgba(24, 144, 255, 0.2)',
      data: [],
      height: '100%',
      line: true,
      borderColor: '#1089ff',
      borderWidth: 2,
      defaultOptions: {
        color: [
          '#1890FF',
          '#2FC25B',
          '#FACC14',
          '#223273',
          '#8543E0',
          '#13C2C2',
          '#3436C7',
          '#F04864'
        ],
        tooltip: {
          textStyle: {
            fontSize: 12
          },
          padding: [10, 5]
        },
        grid: {
          left: 0,
          right: 0,
          top: 10,
          bottom: 5,
          containLabel: true
        },
        xAxis: {
          splitLine: {
            show: false
          },
          axisLabel: {
            color: '#797979',
            margin: 12
          },
          axisTick: {
            show: true,
            alignWithLabel: true
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#bebebe'
            }
          },
          axisPointer: {
            status: 'hide'
          }
        },
        miniXAxis: {
          axisLabel: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisPointer: {
            status: 'hide'
          }
        },
        yAxis: {
          axisLabel: {
            color: '#797979'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine: {
            show: true,
            lineStyle: {
              type: 'dotted'
            }
          }
        },
        series: {
          line: {
            itemStyle: {
              shadowBlur: 15,
              borderWidth: 1,
              borderColor: '#fff'
            },
            getItemStyle: (color) => {
              return {
                color: color,
                shadowColor: color,
                shadowBlur: 15,
                borderWidth: 1,
                borderColor: '#fff'
              }
            },
            showSymbol: false,
            symbol: 'circle',
            symbolSize: 2
          }
        }
      }
    }
  },
  mounted () {
    this.timer = setInterval(() => {
      this.activeData = getActiveData()
    }, 1000)
  },
  computed: {
    options () {
      return {
        grid: {
          left: 0,
          right: 0,
          top: '10%',
          bottom: 0
        },
        tooltip: {
          ...this.defaultOptions.tooltip,
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            lineStyle: {
              width: 0
            }
          },
          formatter: (paramsList) => {
            return paramsList.map((item) => {
              return `${colorSpan(item.color)}${item.value[0]}: ${
                item.value[1]
              }`
            })
          },
          confine: true
        },
        xAxis: {
          ...this.defaultOptions.miniXAxis,
          type: 'category',
          boundaryGap: false,
          data: this.activeData.map(item => {
            return item.x
          })
        },
        yAxis: {
          show: false
        },
        series: [
          {
            type: 'line',
            smooth: true,
            itemStyle: {
              color: this.color,
              shadowColor: this.color,
              shadowBlur: 15,
              borderWidth: 1,
              borderColor: '#fff'
            },
            areaStyle: {
              color: this.color,
              opacity: 1
            },
            showSymbol: false,
            symbol: 'circle',
            symbolSize: 2,
            lineStyle: {
              width: this.line ? this.borderWidth : 0,
              color: this.borderColor
            },
            data: this.activeData.map(item => {
              return [item.x, item.y]
            })
          }
        ]
      }
    }
  },
  beforeDestroy () {
    clearInterval(this.timer)
  }
}
</script>

<style lang="scss">
.active-chart {
  position: relative;
}
.active-chart-grid {
  p {
    position: absolute;
    top: 80px;
  }
  p:last-child {
    top: 115px;
  }
}
.active-chart-legend {
  position: relative;
  font-size: 0;
  margin-top: 8px;
  height: 20px;
  line-height: 20px;
  span {
    display: inline-block;
    font-size: 12px;
    text-align: center;
    width: 33.33%;
  }
  span:first-child {
    text-align: left;
  }
  span:last-child {
    text-align: right;
  }
}
</style>
