<template>
  <div class="index">
    <el-row :gutter="12">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>访问量</span>
            <el-tag style="float: right;">日</el-tag>
          </div>
          <span style="font-size: 30px;">1,970</span>
            <ve-line height="50px" :legend-visible="false" 
            :xAxis="[
                {
                  type : 'category',
                  boundaryGap : false,
                  show: false
                  }
                ]" 
            :yAxis="[
                  {
                      type : 'value',
                      show: false
                  }
                ]" 
            :data="{
                    columns: ['日期', '访问用户', '下单用户', '下单率'],
                    rows: [
                      { '日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32 },
                      { '日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26 },
                      { '日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76 },
                      { '日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49 },
                      { '日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323 },
                      { '日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78 }
                    ]
                }" 
            :settings="{
                  stack: { '用户': ['访问用户', '下单用户'] },
                  area: true
                }">
            </ve-line>
         
          <el-divider></el-divider>
          <el-row>
            <el-col :span="12">总访问人数</el-col>
            <el-col :span="12" class="pull-right">300w</el-col>
          </el-row>
        </el-card>
      </el-col>
     <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>订单</span>
            <el-tag type="danger" style="float: right;">周</el-tag>
          </div>
          <span style="font-size: 30px;">340w</span>
            <ve-histogram height="50px" :legend-visible="false" 
            :xAxis="[
                {
                  type : 'category',
                  show: false
                  }
                ]" 
            :yAxis="[
                  {
                      type : 'value',
                      show: false
                  }
                ]" 
            :data="{
                    columns: ['日期', '访问用户', '下单用户', '下单率'],
                    rows: [
                      { '日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32 },
                      { '日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26 },
                      { '日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76 },
                      { '日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49 },
                      { '日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323 },
                      { '日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78 }
                    ]
                }" 
            :settings="{
                  stack: { '用户': ['访问用户', '下单用户'] },
                  area: true
                }">
            </ve-histogram>
         
          <el-divider></el-divider>
          <el-row>
            <el-col :span="12">总订单</el-col>
            <el-col :span="12" class="pull-right">1340w</el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>销售额</span>
            <el-tag type="warning" style="float: right;">月</el-tag>
          </div>
          <span style="font-size: 30px">3,123</span>
            <div style="height:50px; padding: 10px">
              <el-progress :text-inside="true" :stroke-width="26" :percentage="70"></el-progress>
            </div>
          <el-divider></el-divider>
          <el-row>
            <el-col :span="12">总销售额</el-col>
            <el-col :span="12" class="pull-right">780w</el-col>
          </el-row>
        </el-card>
      </el-col>

           <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>新用户</span>
            <el-tag type="success" style="float: right;">年</el-tag>
          </div>
          <span style="font-size: 30px;">7,780</span>
          <div style="height:50px;padding-left:100px; padding-top:-10px;">
          <el-progress type="dashboard" :percentage="10" :width="80" :color=" [
              {color: '#f56c6c', percentage: 20},
              {color: '#e6a23c', percentage: 40},
              {color: '#5cb87a', percentage: 60},
              {color: '#1989fa', percentage: 80},
              {color: '#6f7ad3', percentage: 100}
            ]"></el-progress>
          </div>
         
          <el-divider></el-divider>
          <el-row>
            <el-col :span="12">总用户</el-col>
            <el-col :span="12" class="pull-right">23w</el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-top:10px">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>订单转化率</span>
          </div>
          <ve-histogram :data=" {
            columns: ['日期', '访问用户', '下单用户', '下单率'],
            rows: [
              { '日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32 },
              { '日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26 },
              { '日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76 },
              { '日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49 },
              { '日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323 },
              { '日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78 }
            ]
        }" :settings="{
        showLine: ['下单用户']
      }"></ve-histogram>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>市场分析</span>
          </div>
          <ve-liquidfill :data="{
        columns: ['city', 'percent'],
        rows: [{
          city: '上海',
          percent: 0.6
        }, {
          city: '广州',
          percent: 0.3
        }, {
          city: '北京',
          percent: 0.5
        }]
      }" :settings="{
      wave: [[0.5, 0.3, 0.1], [0.3, 0.2], []],
      seriesMap: [
        {
          color: ['red', 'green', 'yellow'],
          label: {
            formatter (options) {
              const {
                seriesName,
                data: {
                  value
                }
              } = options
              return `${seriesName}\n${value}`
            },
            fontSize: 30
          },
          center: ['24%', '20%'],
          radius: '40%',
          waveAnimation: true
        },
        {
           color: ['red', 'green', 'yellow'],
          label: {
            formatter (options) {
              return `${options.seriesName}\n${options.data.value}`
            },
            fontSize: 30
          },
          center: ['25%', '70%'],
          radius: '40%'
        },
        {
           color: ['red', 'green', 'yellow'],
          label: {
             formatter (options) {
              return `${options.seriesName}\n${options.data.value}`
            },
            fontSize: 30
          },
          center: ['70%', '50%'],
          radius: '40%',
          waveAnimation: true
        }
      ]
    }"></ve-liquidfill>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-top:10px">
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>主机事例</span>
          </div>
          <ve-ring :data=" {
          columns: ['日期', '访问用户'],
          rows: [
            { '日期': '事例一', '访问用户': 1393 },
            { '日期': '事例二', '访问用户': 3530 },
            { '日期': '事例三', '访问用户': 2923 },
            { '日期': '事例四', '访问用户': 1723 },
            { '日期': '事例五', '访问用户': 3792 }
          ]
        }"></ve-ring>
        </el-card>
      </el-col>
  
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="6">
        <el-card>
          <div slot="header" class="clearfix">
            <span>品牌统计</span>
          </div>
          <ve-wordcloud :data="{
          columns: ['word', 'count'],
          rows: [{
            'word': 'addidas',
            'count': 22199
          }, {
            'word': 'nike',
            'count': 10288
          }, {
            'word': 'lining',
            'count': 620
          }, {
            'word': 'series',
            'count': 274470
          }, {
            'word': 'gauge',
            'count': 12311
          }, {
            'word': 'detail',
            'count': 1206
          }, {
            'word': 'piecewise',
            'count': 4885
          }, {
            'word': 'textStyle',
            'count': 32294
          }, {
            'word': 'markPoint',
            'count': 18574
          }, {
            'word': 'pie',
            'count': 38929
          }, {
            'word': 'roseType',
            'count': 969
          }, {
            'word': 'label',
            'count': 37517
          }, {
            'word': 'emphasis',
            'count': 12053
          }, {
            'word': 'yAxis',
            'count': 57299
          }, {
            'word': 'name',
            'count': 15418
          }, {
            'word': 'type',
            'count': 22905
          }, {
            'word': 'gridIndex',
            'count': 5146
          }, {
            'word': 'normal',
            'count': 49487
          }, {
            'word': 'itemStyle',
            'count': 33837
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
            'word': 'offsetCenter',
            'count': 232
          }, {
            'word': 'inverse',
            'count': 3706
          }, {
            'word': 'borderColor',
            'count': 4812
          }, {
            'word': 'markLine',
            'count': 16578
          }, {
            'word': 'line',
            'count': 76970
          }, {
            'word': 'radiusAxis',
            'count': 6704
          }, {
            'word': 'radar',
            'count': 15964
          }, {
            'word': 'data',
            'count': 60679
          }, {
            'word': 'dataZoom',
            'count': 24347
          }, {
            'word': 'tooltip',
            'count': 43420
          }, {
            'word': 'toolbox',
            'count': 25222
          }, {
            'word': 'geo',
            'count': 16904
          }, {
            'word': 'parallelAxis',
            'count': 4029
          }]
        }" :settings="{
              shape: 'diamond'
            }"></ve-wordcloud>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="margin-top:10px">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>股票走势</span>
            <div class="block" style="float: right;margin-top:-8px;" >
            </div>
          </div>
          <ve-candle :data=" {
          columns: ['日期', 'open', 'close', 'lowest', 'highest', 'vol'],
          rows: [
            { '日期': '2004-01-05', open: 10411.85, close: 10544.07, lowest: 10411.85, highest: 10575.92, vol: 221290000 },
            { '日期': '2004-01-06', open: 10543.85, close: 10538.66, lowest: 10454.37, highest: 10584.07, vol: 191460000 },
            { '日期': '2004-01-07', open: 10535.46, close: 10529.03, lowest: 10432.12, highest: 10587.55, vol: 225490000 },
            { '日期': '2004-01-08', open: 10530.07, close: 10592.44, lowest: 10480.59, highest: 10651.99, vol: 237770000 },
            { '日期': '2004-01-09', open: 10589.25, close: 10458.89, lowest: 10420.52, highest: 10603.48, vol: 223250000 },
            { '日期': '2004-01-12', open: 10461.55, close: 10485.18, lowest: 10389.85, highest: 10543.03, vol: 197960000 },
            { '日期': '2004-01-13', open: 10485.18, close: 10427.18, lowest: 10341.19, highest: 10539.25, vol: 197310000 },
            { '日期': '2004-01-14', open: 10428.67, close: 10538.37, lowest: 10426.89, highest: 10573.85, vol: 186280000 },
            { '日期': '2004-01-15', open: 10534.52, close: 10553.85, lowest: 10454.52, highest: 10639.03, vol: 260090000 },
            { '日期': '2004-01-16', open: 10556.37, close: 10600.51, lowest: 10503.71, highest: 10666.88, vol: 254170000 },
            { '日期': '2004-01-20', open: 10601.42, close: 10528.66, lowest: 10447.92, highest: 10676.96, vol: 224300000 },
            { '日期': '2004-01-21', open: 10522.77, close: 10623.62, lowest: 10453.11, highest: 10665.72, vol: 214920000 },
            { '日期': '2004-01-22', open: 10624.22, close: 10623.18, lowest: 10545.03, highest: 10717.41, vol: 219720000 },
            { '日期': '2004-01-23', open: 10625.25, close: 10568.29, lowest: 10490.14, highest: 10691.77, vol: 234260000 },
            { '日期': '2004-01-26', open: 10568.12, close: 10702.51, lowest: 10510.44, highest: 10725.18, vol: 186170000 },
            { '日期': '2004-01-27', open: 10701.11, close: 10609.92, lowest: 10579.33, highest: 10748.81, vol: 206560000 },
            { '日期': '2004-01-28', open: 10610.07, close: 10468.37, lowest: 10412.44, highest: 10703.25, vol: 247660000 },
            { '日期': '2004-01-29', open: 10467.41, close: 10510.29, lowest: 10369.92, highest: 10611.56, vol: 273970000 },
            { '日期': '2004-01-30', open: 10510.22, close: 10488.07, lowest: 10385.56, highest: 10551.03, vol: 208990000 },
            { '日期': '2004-02-02', open: 10487.78, close: 10499.18, lowest: 10395.55, highest: 10614.44, vol: 224800000 },
            { '日期': '2004-02-03', open: 10499.48, close: 10505.18, lowest: 10414.15, highest: 10571.48, vol: 183810000 },
            { '日期': '2004-02-04', open: 10503.11, close: 10470.74, lowest: 10394.81, highest: 10567.85, vol: 227760000 },
            { '日期': '2004-02-05', open: 10469.33, close: 10495.55, lowest: 10399.92, highest: 10566.37, vol: 187810000 },
            { '日期': '2004-02-06', open: 10494.89, close: 10593.03, lowest: 10433.74, highest: 10634.81, vol: 182880000 },
            { '日期': '2004-02-09', open: 10592.41, close: 10579.03, lowest: 10433.72, highest: 10634.81, vol: 160720000 },
            { '日期': '2004-02-10', open: 10578.74, close: 10613.85, lowest: 10511.18, highest: 10667.03, vol: 160590000 },
            { '日期': '2004-02-11', open: 10605.48, close: 10737.72, lowest: 10561.55, highest: 10779.41, vol: 277850000 },
            { '日期': '2004-02-12', open: 10735.18, close: 10694.07, lowest: 10636.44, highest: 10775.03, vol: 197560000 },
            { '日期': '2004-02-13', open: 10696.22, close: 10627.85, lowest: 10578.66, highest: 10755.47, vol: 208340000 },
            { '日期': '2004-02-17', open: 10628.88, close: 10714.88, lowest: 10628.88, highest: 10762.07, vol: 169730000 },
            { '日期': '2004-02-18', open: 10706.68, close: 10671.99, lowest: 10623.62, highest: 10764.36, vol: 164370000 },
            { '日期': '2004-02-19', open: 10674.59, close: 10664.73, lowest: 10626.44, highest: 10794.95, vol: 219890000 },
            { '日期': '2004-02-20', open: 10666.29, close: 10619.03, lowest: 10559.11, highest: 10722.77, vol: 220560000 },
            { '日期': '2004-02-23', open: 10619.55, close: 10609.62, lowest: 10508.89, highest: 10711.84, vol: 229950000 },
            { '日期': '2004-02-24', open: 10609.55, close: 10566.37, lowest: 10479.33, highest: 10681.41, vol: 225670000 },
            { '日期': '2004-02-25', open: 10566.59, close: 10601.62, lowest: 10509.42, highest: 10660.73, vol: 192420000 },
            { '日期': '2004-02-26', open: 10598.14, close: 10580.14, lowest: 10493.71, highest: 10652.96, vol: 223230000 },
            { '日期': '2004-02-27', open: 10581.55, close: 10583.92, lowest: 10519.03, highest: 10689.55, vol: 200050000 }
          ]
        }" :settings="{
              showMA: true,
              showVol: true
            }">
          </ve-candle>
        </el-card>
      </el-col>
    </el-row>
    <!-- <el-row>
      <el-col :xs="24" :sm="24" :md="22" :lg="24" :xl="24">
        <el-card>
          <chart-calendar id="c5"></chart-calendar>
        </el-card>
      </el-col>
    </el-row> -->
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      pickerOptions2: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      value7: ''
    }
  }
}
</script>
<style scoped lang="scss">
.widget{
  padding:20px
}
.img-avatar-48 {
    width: 48px;
    height: 48px;
    line-height: 48px;
}

.img-avatar {
    display: inline-block !important;
    width: 64px;
    height: 64px;
    line-height: 64px;
    text-align: center;
    vertical-align: middle;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}
.bg-translucent {
    background-color: rgba(255, 255, 255, 0.175);
}
.fa-1-5x {
    font-size: 1.5em;
    color: #ffffff;
}
</style>
