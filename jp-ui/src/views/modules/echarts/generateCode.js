
export default function (data, dataUrl, datatype = 'static', type = 'line', custom = true) {
  let createdTemplate = ''
  let chartData = `{
          }`
  let colorArra = '['
  for (var i = 0; i < data.colors.length; i++) {
    if (i < data.colors.length - 1) {
      colorArra = colorArra + `'${data.colors[i]}', `
    } else {
      colorArra = colorArra + `'${data.colors[i]}'`
    }
  }
  colorArra = colorArra + ']'
  if (datatype === 'dy') {
    createdTemplate = `,
      echartsService: null,
      created () {
        this.echartsService = new EchartsService()
      },
      activated () {
        this.echartsService.getChartData(this.$route.query.id).then(({data}) => {
          this.chartData = data.chartData
        })
      }`
  }
  if (datatype === 'static') {
    let columns = '['
    let cData = dataUrl
    for (var j = 0; j < cData.columns.length; j++) {
      if (j < cData.columns.length - 1) {
        columns = columns + `'${cData.columns[j]}', `
      } else {
        columns = columns + `'${cData.columns[j]}'`
      }
    }
    columns = columns + ']'
    let rows = '['
    for (var k = 0; k < cData.rows.length; k++) {
      rows = rows + `
            {`
      for (var m = 0; m < cData.columns.length; m++) {
        if (m < cData.columns.length - 1) {
          rows = rows + `'${cData.columns[m]}': ${typeof cData.rows[k][cData.columns[m]] === 'number' ? cData.rows[k][cData.columns[m]] : `'` + cData.rows[k][cData.columns[m]] + `'`}, `
        } else {
          rows = rows + `'${cData.columns[m]}': ${typeof cData.rows[k][cData.columns[m]] === 'number' ? cData.rows[k][cData.columns[m]] : `'` + cData.rows[k][cData.columns[m]] + `'`}`
        }
      }
      if (k < cData.rows.length - 1) {
        rows = rows + `},`
      } else {
        rows = rows + `}`
      }
    }
    rows = rows + ']'
    chartData = `{
            'columns': ${columns},
            'rows': ${rows}
          }`
  }
  let optionTemplate = ` {
            title: {
              show: ${data.title.show},
              text: '${data.title.text}',
              left: '${data.title.left}',
              textStyle: {
                color: '${data.title.textStyle.color}',
                fontSize: ${data.title.textStyle.fontSize}
              },
              subtext: '${data.title.subtext}',
              subtextStyle: {
                color: '${data.title.subtextStyle.color}',
                fontSize: ${data.title.subtextStyle.fontSize}
              }
            },
            tooltip: {
              textStyle: {
                color: '${data.tooltip.textStyle.color}',
                fontSize: ${data.tooltip.textStyle.fontSize}
              }
            },
            grid: {
              left: ${data.grid.left},
              top: ${data.grid.top},
              right: ${data.grid.right},
              bottom: ${data.grid.bottom}
            },
            legend: {
              show: ${data.legend.show},
              orient: '${data.legend.orient}',
              textStyle: {
                fontSize: ${data.legend.textStyle.fontSize},
                color: '${data.legend.textStyle.color}'
              }
            },
            extend: {
              series: {
                label: {
                  normal: {
                    show: ${data.extend.series.label.normal.show},
                    position: '${data.extend.series.label.normal.position}',
                    fontSize: ${data.extend.series.label.normal.fontSize},
                    color: '${data.extend.series.label.normal.color}',
                    fontWeight: '${data.extend.series.label.normal.fontWeight}'
                  }
                },
                barMaxWidth: ${data.extend.series.barMaxWidth},
                barMinHeight: ${data.extend.series.barMinHeight}
              }
            },
            colors: ${colorArra}
          }`
  if (type === 'table') {
    return `<template>
    <el-table :data="chartData.rows">
        <el-table-column
          v-for="(column, index) in chartData.columns" :key="index"
          :prop="column"
          :label="column">
        </el-table-column>
    </el-table>
</template>
  
<script>
    export default {
      data () {
        return {
          chartData: ${chartData}
        }
      }${createdTemplate}
    }
</script>`
  } else if (custom) {
    return `<template>
    <ve-${type}
      :data="chartData"
      :legend="activeOption.legend"
      :grid="activeOption.grid" 
      :extend="activeOption.extend"
      :tooltip="activeOption.tooltip"
      :colors="activeOption.colors"
      :title="activeOption.title"
      >
    </ve-${type}>
</template>

<script>
    ${datatype === 'dy' ? `import EchartsService from '@/api/echarts/EchartsService'` : ''}
    export default {
      data () {
        return {
          chartData: ${chartData},
          activeOption:${optionTemplate}
        }
      }${createdTemplate}
    }
</script>`
  } else {
    return `<template>
    <ve-${type} :data="chartData">
    </ve-${type}>
</template>
  
<script>
    export default {
      data () {
        return {
          chartData: ${chartData}
        }
      }${createdTemplate}
    }
</script>`
  }
}
