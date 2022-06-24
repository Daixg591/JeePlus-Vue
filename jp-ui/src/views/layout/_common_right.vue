<template>
<div class="right">
    <el-drawer
    
      :visible.sync="isRightVisible"
      size="250px"
      :modal="true"
      show-close
      :with-header="true"
      :direction="direction">
     <el-form style="padding:10px">  
       <el-form-item>
      <el-divider>导航模式</el-divider>
      <el-radio-group v-model="defaultLayout">
         <el-tooltip class="item" effect="dark" content="横向菜单" placement="top-start">
           <el-radio label="top">
            <img src="~@/assets/img/top_layout.svg"/>
          </el-radio>
         </el-tooltip>
         <el-tooltip class="item" effect="dark" content="左侧菜单" placement="top-start">
          <el-radio label="left">
            <img src="~@/assets/img/left_layout.svg"/>
          </el-radio>
        </el-tooltip>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
       <el-divider>主题风格</el-divider>
      <el-radio-group v-model="navbarLayoutType">

           <el-radio class="item m-b-20" label="1">
            <img src="~@/assets/img/1.svg"/>
          </el-radio>
          <el-radio class="item m-b-20" label="3">
            <img src="~@/assets/img/3.svg"/>
          </el-radio>
          <el-radio class="item m-b-20" label="7">
            <img src="~@/assets/img/7.svg"/>
          </el-radio>
           <el-radio class="item m-b-20" label="4">
            <img src="~@/assets/img/4.svg"/>
          </el-radio>
          <el-radio class="item m-b-20" label="2">
            <img src="~@/assets/img/2.svg"/>
          </el-radio>
           <el-radio class="item m-b-20" label="8">
            <img src="~@/assets/img/8.svg"/>
          </el-radio>
           <el-radio class="item m-b-20" label="5">
            <img src="~@/assets/img/5.svg"/>
          </el-radio>
           <el-radio label="9">
            <img src="~@/assets/img/9.svg"/>
          </el-radio>
          <el-radio label="6">
            <img src="~@/assets/img/6.svg"/>
          </el-radio>
      </el-radio-group>
    </el-form-item>
     <el-form-item>
          <el-divider>主题色</el-divider>
          <div class="tag-group">
            <el-tooltip effect="dark" :content="item.key" placement="top-start" v-for="(item, index) in colorList" :key="index">
              <el-tag :color="item.color" class="themeColorTag" @click="defaultTheme = item.color">
                <i class="el-icon-check themeColorFont" v-if="item.color === defaultTheme"></i>
              </el-tag>
            </el-tooltip>
          </div>
    </el-form-item>
    <el-divider>其它设置</el-divider>
    <el-form-item label="多页签模式"  class="title-setting">
       <el-switch
          v-model="isTab">
        </el-switch>
    </el-form-item>
  </el-form>
    </el-drawer>
</div>
</template>

<script>
  export default {
    data () {
      return {
        isRightVisible: false,
        direction: 'rtl',
        colorList: [
          {
            key: '拂晓蓝（默认）', color: '#1890FF'
          },
          {
            key: '薄暮', color: '#F5222D', label: '1'
          },
          {
            key: '火山', color: '#FA541C', label: '2'
          },
          {
            key: '日暮', color: '#FAAD14'
          },
          {
            key: '明青', color: '#13C2C2'
          },
          {
            key: '极光绿', color: '#52C41A'
          },
          {
            key: '极客蓝', color: '#2F54EB'
          },
          {
            key: '酱紫', color: '#722ED1'
          },
          {
            key: '天空蓝', color: '#3e8df7'
          },
          {
            key: '咖啡色', color: '#9a7b71'
          },
          {
            key: '深湖蓝', color: '#07b2d3'
          },
          {
            key: '原谅绿', color: '#0cc26c'
          },
          {
            key: '古铜灰', color: '#757575'
          },
          {
            key: '珊瑚紫', color: '#6779fa'
          },
          {
            key: '橙黄', color: '#eb6607'
          },
          {
            key: '粉红', color: '#f74584'
          },
          {
            key: '青紫', color: '#9463f7'
          },
          {
            key: '橄榄绿', color: '#16b2a3'
          }
        ]
      }
    },
    computed: {
      defaultLayout: {
        get () {
          return this.$store.state.config.defaultLayout
        },
        set (val) {
          localStorage.setItem('defaultLayout', val)
          this.$store.commit('config/updateDefaultLayout', val)
        }
      },
      navbarLayoutType: {
        get () {
          return this.$store.state.common.navbarLayoutType
        },
        set (val) {
          localStorage.setItem('navbarLayoutType', val)
          this.$store.commit('common/updateNavbarLayoutType', val)
          localStorage.setItem('sidebarLayoutSkin', val)
          this.$store.commit('common/updateSidebarLayoutSkin', val)
        }
      },
      // sidebarLayoutSkin: {
      //   get () {
      //     return this.$store.state.common.sidebarLayoutSkin
      //   },
      //   set (val) {
      //     localStorage.setItem('sidebarLayoutSkin', val)
      //     this.$store.commit('common/updateSidebarLayoutSkin', val)
      //   }
      // },
      isTab: {
        get () {
          return this.$store.state.common.isTab
        },
        set (val) {
          localStorage.setItem('isTab', val)
          this.$store.commit('common/updateIsTab', val)
        }
      },
      defaultTheme: {
        get () {
          return this.$store.state.config.defaultTheme
        },
        set (val) {
          localStorage.setItem('defaultTheme', val)
          this.$events.$emit('updateTheme', val)
          return this.$store.commit('config/updateDefaultTheme', val)
        }
      }
    },
    methods: {
      showRight () {
        this.isRightVisible = true
      }
    }
  }
</script>
<style>
.right  .el-drawer__header {
    margin-bottom: 0px;
}
.block{
  display: block
}
.m-b-20 {
  margin-bottom: 20px
}
.themeColorTag{
  width:25px !important; 
  height:25px !important; 
  margin-left: 0px !important;
  margin-bottom: 0px !important;
}
.themeColorFont{
  position: absolute;
  color: #fff;
  margin-top: 3px;
  margin-left: -6px;
  font-weight: bold;
  font-size: 16px;
}
.title-setting{
    margin-bottom: 12px;
    color: rgba(0,0,0,.85);
    font-size: 14px;
    line-height: 22px;
}
.title-setting .el-form-item__label{
    color: rgba(0,0,0,.85);
    font-size: 14px;
    font-weight: bold;
}
</style>