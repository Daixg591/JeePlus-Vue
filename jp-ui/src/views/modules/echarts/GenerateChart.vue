<template>
  <div class="page bg-white">
    <el-table v-if="type === 'table'"
    border
      :data="chartData.rows">
          <el-table-column
          v-for="(column, index) in chartData.columns" :key="index"
            :prop="column"
            :label="column">
          </el-table-column>
    </el-table>
    <component :is="'ve-'+type" v-else-if="custom"
      :data="chartData"
      :legend="activeOption.legend"
      :grid="activeOption.grid" 
      :extend="activeOption.extend"
      :tooltip="activeOption.tooltip"
      :colors="activeOption.colors"
      :title="activeOption.title"
      />

      <component :is="'ve-'+type" v-else
      :data="chartData"/>
  </div>
</template>

<script>
  import EchartsService from '@/api/echarts/EchartsService'
  export default {
    data () {
      return {
        custom: true,
        type: 'line',
        chartData: {
        },
        activeOption: {
          title: {
            show: true,
            text: '',
            left: 'auto',
            textStyle: {
              color: '#333',
              fontSize: 18
            },
            subtext: '',
            subtextStyle: {
              color: '#aaa',
              fontSize: 12
            }
          },
          tooltip: {
            textStyle: {
              color: '#fff',
              fontSize: 14
            }
          },
          grid: {
            left: 10,
            top: 60,
            right: 10,
            bottom: 60
          },
          legend: {
            show: true,
            orient: 'horizontal',
            textStyle: {
              fontSize: 12,
              color: '#333'
            }
          },
          extend: {
            series: {
              label: {
                normal: {
                  show: false,
                  position: 'top',
                  fontSize: 12,
                  color: '#333',
                  fontWeight: 'normal'
                }
              },
              barMaxWidth: 0,
              barMinHeight: 0
            }
          },
          colors: []
        }
      }
    },
    echartsService: null,
    created () {
      this.echartsService = new EchartsService()
    },
    activated () {
      this.echartsService.getChartData(this.$route.query.id).then(({data}) => {
        if (data.echarts.option === '') {
          this.custom = false
        } else {
          this.activeOption = JSON.parse(data.echarts.option)
        }
        this.type = data.echarts.type
        this.chartData = data.chartData
      })
    }
  }
</script>