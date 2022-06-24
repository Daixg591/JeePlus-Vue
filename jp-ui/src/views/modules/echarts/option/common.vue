<!-- 通用配置-->
<template>
  <div>
    <!-- 折叠公共配置 -->
    <el-collapse accordion>
      <!-- 标题设置 -->
      <template v-if="main.vaildProp('titleList')">
        <el-collapse-item title="标题设置">
          <el-form-item label="标题">
            <el-switch v-model="main.activeOption.title.show"></el-switch>
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="main.activeOption.title.text"></el-input>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker v-model="main.activeOption.title.textStyle.color"></el-color-picker>
          </el-form-item>
          <el-form-item label="字体大小">
            <el-input-number v-model="main.activeOption.title.textStyle.fontSize"></el-input-number>
          </el-form-item>
          <el-form-item label="字体位置">
            <el-select v-model="main.activeOption.title.left" placeholder="请选择">
              <el-option
                v-for="item in dicOption.textAlign"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="副标题">
            <el-input v-model="main.activeOption.title.subtext"></el-input>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker v-model="main.activeOption.title.subtextStyle.color"></el-color-picker>
          </el-form-item>
          <el-form-item label="字体大小">
            <el-input-number v-model="main.activeOption.title.subtextStyle.fontSize">
            </el-input-number>
          </el-form-item>
        </el-collapse-item>
      </template>
      <!-- 数值设置 -->
      <template v-if="main.vaildProp('labelList')">
        <el-collapse-item title="数值设置">
          <el-form-item label="显示">
            <el-switch v-model="main.activeOption.extend.series.label.normal.show">
            </el-switch>
          </el-form-item>
           <el-form-item label="位置">
              <el-select v-model="main.activeOption.extend.series.label.normal.position" placeholder="请选择">
                <el-option
                  v-for="item in dicOption.labelPosition"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字体大小">
            <el-input-number v-model="main.activeOption.extend.series.label.normal.fontSize">
            </el-input-number>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker v-model="main.activeOption.extend.series.label.normal.color">
            </el-color-picker>
          </el-form-item>
          <el-form-item label="字体粗细">
              <el-select v-model="main.activeOption.extend.series.label.normal.fontWeight" placeholder="请选择">
                <el-option
                  v-for="item in dicOption.fontWeight"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
        </el-collapse-item>
      </template>
      <!-- 提示语设置  -->
      <template v-if="main.vaildProp('tipList')">
        <el-collapse-item title="提示语设置">
          <el-form-item label="字体大小">
            <el-input-number v-model="main.activeOption.tooltip.textStyle.fontSize"></el-input-number>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker v-model="main.activeOption.tooltip.textStyle.color"></el-color-picker>
          </el-form-item>
        </el-collapse-item>
      </template>
      <!-- 轴距离设置 -->
      <template v-if="main.vaildProp('postionList')">
        <el-collapse-item title="坐标轴边距设置">
          <el-form-item label="左边距(像素)">
            <el-slider v-model="main.activeOption.grid.left"
                         :max="400"></el-slider>
          </el-form-item>
          <el-form-item label="顶边距(像素)">
            <el-slider v-model="main.activeOption.grid.top"
                         :max="400"></el-slider>
          </el-form-item>
          <el-form-item label="右边距(像素)">
            <el-slider v-model="main.activeOption.grid.right"
                         :max="400"></el-slider>
          </el-form-item>
          <el-form-item label="底边距(像素)">
            <el-slider v-model="main.activeOption.grid.bottom"
                         :max="400"></el-slider>
          </el-form-item>
        </el-collapse-item>
      </template>
            <!-- 柱体设置 -->
      <template v-if="main.vaildProp('barList')">
        <el-collapse-item title="柱体设置">
          <el-form-item label="最大宽度">
            <el-slider v-model="main.activeOption.extend.series.barMaxWidth"
                         :max="200"></el-slider>
          </el-form-item>
          <el-form-item label="最小高度">
            <el-slider v-model="main.activeOption.extend.series.barMinHeight"
                         :max="100"></el-slider>
          </el-form-item>
        </el-collapse-item>
      </template>
      <!-- 图例设置 -->
      <template v-if="main.vaildProp('legendList')">
        <el-collapse-item title="图例操作">
          <el-form-item label="图例">
            <el-switch v-model="main.activeOption.legend.show"></el-switch>
          </el-form-item>
          <el-form-item label="位置">
              <el-select v-model="main.activeOption.legend.orient" placeholder="请选择">
                <el-option
                  v-for="item in dicOption.orientList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字体大小">
            <el-input-number v-model="main.activeOption.legend.textStyle.fontSize"></el-input-number>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker v-model="main.activeOption.legend.textStyle.color"></el-color-picker>
          </el-form-item>
        </el-collapse-item>
      </template>
      <!-- 颜色设置 -->
      <template v-if="main.vaildProp('colorList')">
        <el-collapse-item title="自定义配色">
                     <el-button type="primary" @click="addColor" size="mini">添加</el-button>
                      <el-table
                      :data="dataColors"
                      style="width: 100%">
                      <el-table-column
                        type="index">
                      </el-table-column>
                    
                      <el-table-column
                        prop="color"
                        label="颜色">
                         <template slot-scope="scope">
                           <el-color-picker v-model="scope.row.color"></el-color-picker>
                         </template>
                      </el-table-column>
                      <el-table-column
                        fixed="right"
                        :key="Math.random()"
                        label="操作"
                        width="120">
                        <template slot-scope="scope">
                          <el-button
                            @click.native.prevent="dataColors.splice(scope.$index, 1)"
                            type="text"
                            size="small">
                            移除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
        </el-collapse-item>
      </template>
    </el-collapse>
  </div>
</template>

<script>
import { dicOption } from './config'
export default {
  inject: ['main'],
  data () {
    return {
      dicOption: dicOption,
      dataColors: [{'color': '#6395f9'}, {'color': '#64daab'}, {'color': '#657698'}, {'color': '#f6c02d'}, {'color': '#e96d5b'}]
    }
  },
  watch: {
    dataColors: {
      handler (val) {
        this.main.activeOption.colors = val.map((m) => {
          return m.color
        })
        this.main.activeOption = JSON.parse(JSON.stringify(this.main.activeOption))
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    addColor () {
      this.dataColors.push({'color': '#6395f9'})
    }
  }
}
</script>

<style>
</style>