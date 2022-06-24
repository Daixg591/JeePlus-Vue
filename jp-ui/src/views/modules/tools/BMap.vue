<template>
<div class="el-scrollbar__wrap wrap-white">
  <div class="el-scrollbar__view">
  <!--百度地图容器-->
  <div style='width:100%;height:800px;border:#ccc solid 1px;' id='dituContent'></div>
  <el-dialog :title='title'   v-dialogDrag :visible.sync='dialogTableVisible'>
    <img width="500px" v-if="index === 1" height="300px" src="~@/assets/img/zjs.png" />
    <img width="500px" v-if="index === 2" height="300px" src="~@/assets/img/xwh.png" />
  </el-dialog>
  </div>
</div>
</template>
<script>
// 引入富文本编辑器所需样式
import {MP} from './map.js'
export default {
  data () {
    return {
      ak: 'I7ioGmQbypWKuWMNv5fLPKU4mt3gzhrz', // 正式线上项目，务必替换成自己的key
      dialogTableVisible: false,
      BMap: null,
      map: null,
      title: '',
      imgUrl: '',
      index: 1,
      markerArr1: [{title: '紫金山', content: '~@/assets/img/zjs.png', point: '118.853196|32.08811', isOpen: 0, icon: {w: 23, h: 25, l: 46, t: 21, x: 9, lb: 12}},
      {title: '玄武湖', content: '~@/assets/img/xwh.png', point: '118.804616|32.080278', isOpen: 0, icon: {w: 23, h: 25, l: 46, t: 21, x: 9, lb: 12}}
      ]
    }
  },
  mounted () {
    let _this = this
    MP(this.ak).then(BMap => {
          // 百度地图API功能
      _this.BMap = BMap
      _this.initMap()
    })
  },
  methods: {
    initMap () {
          // 百度地图API功能
      this.createMap()// 创建地图
      this.setMapEvent()// 设置地图事件
      this.addMapControl()// 向地图添加控件
      this.addMarker(this.markerArr1)// 向地图中添加marker
    },
    createMap () {
      var map = new this.BMap.Map('dituContent')// 在百度地图容器中创建一个地图
      var point = new this.BMap.Point(118.853196, 32.08811)// 定义一个中心点坐标
      map.centerAndZoom(point, 14)// 设定地图的中心点和坐标并将地图显示在地图容器中
      // map.setMapStyleV2({
      //   styleId: '44ce7d9e957f49e0f17ccee298d7bdd4'
      // })
      this.map = map// 将map变量存储在全局
    },
    setMapEvent () {
      this.map.enableDragging()// 启用地图拖拽事件，默认启用(可不写)
      this.map.enableScrollWheelZoom()// 启用地图滚轮放大缩小
      this.map.enableDoubleClickZoom()// 启用鼠标双击放大，默认启用(可不写)
      this.map.enableKeyboard()// 启用键盘上下左右键移动地图
    },
    addMapControl () {
        // 向地图中添加缩放控件
      var ctrlMav = new this.BMap.NavigationControl({anchor: window.BMAP_ANCHOR_TOP_LEFT, type: window.BMAP_NAVIGATION_CONTROL_LARGE})
      this.map.addControl(ctrlMav)
        // 向地图中添加缩略图控件
      var ctrlOve = new this.BMap.OverviewMapControl({anchor: window.BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: 1})
      this.map.addControl(ctrlOve)
        // 向地图中添加比例尺控件
      var ctrlSca = new this.BMap.ScaleControl({anchor: window.BMAP_ANCHOR_BOTTOM_LEFT})
      this.map.addControl(ctrlSca)
    },
    addMarker (markerArr) {
      let _this = this
      for (var i = 0; i < markerArr.length; i++) {
        var json = markerArr[i]
        var p0 = json.point.split('|')[0]
        var p1 = json.point.split('|')[1]
        var point = new this.BMap.Point(p0, p1)
        var iconImg = this.createIcon(json.icon)
        var marker = new this.BMap.Marker(point, {icon: iconImg})
        let jsonx = this.createInfoWindow(i, markerArr)
        var label = new this.BMap.Label(json.title, {'offset': new this.BMap.Size(json.icon.lb - json.icon.x + 10, -20)})
        marker.setLabel(label)
        this.map.addOverlay(marker)
        label.setStyle({
          borderColor: '#808080',
          color: '#333',
          cursor: 'pointer'
        });
        (function () {
          var index = i
          jsonx = _this.createInfoWindow(i, markerArr)
          var _marker = marker
          _marker.addEventListener('click', function () {
            _this.open(index, jsonx)
          })

          label.addEventListener('click', function () {
            _this.open(index, jsonx)
          })
          if (json.isOpen) {
            label.hide()
          }
        })()
      }
    },
    createInfoWindow (i, markerArr) {
      var json = markerArr[i]

      return json
    },
    open (index, json) {
      this.dialogTableVisible = true
      this.index = index + 1
      this.title = json.title
      this.imgUrl = json.content
    },
    createIcon (json) {
      var icon = new this.BMap.Icon('http://map.baidu.com/image/us_mk_icon.png', new this.BMap.Size(json.w, json.h), {imageOffset: new this.BMap.Size(-json.l, -json.t), infoWindowOffset: new this.BMap.Size(json.lb + 5, 1), offset: new this.BMap.Size(json.x, json.h)})
      return icon
    }
  }
}
</script>

<style lang='scss' scoped>
  .article-box {
    width: 100%;
    .el-pagination {
      margin-top: 20px;
    }
    .quill-editor {
      height: 300px;
      margin-bottom: 20px;
    }
  }
</style>
