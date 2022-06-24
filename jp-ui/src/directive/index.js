/* eslint-disable no-useless-escape */
import Vue from 'vue'
Vue.directive('noMoreClick', {
  inserted (el, binding) {
    el.addEventListener('click', e => {
      el.classList.add('is-disabled')
      el.disabled = true
      setTimeout(() => {
        el.disabled = false
        el.classList.remove('is-disabled')
      }, 2000)
    })
  }
})
/*

*  使用方法

*  将以下代码复制到一个js文件中，然后在入口文件main.js中import引入即可；

*  给elementUI的dialog上加上 v-dialogDrag 指令就可以实现弹窗的全屏和拉伸了。

*  给dialog设置 :close-on-click-modal="false" , 禁止点击遮罩层关闭弹出层

*  如果是form表单，不要将提交等按钮放置el-form-item，以免在上下拉伸时被隐藏

*/

// v-dialogDrag: 弹窗拖拽+水平方向伸缩

// v-dialogDrag: 弹窗拖拽
Vue.directive('dialogDrag', {
  bind (el, binding, vnode, oldVnode) {
    const dialogHeaderEl = el.querySelector('.el-dialog__header')
    const dragDom = el.querySelector('.el-dialog')
    dialogHeaderEl.style.cursor = 'move'

      // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
    const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null)

    dialogHeaderEl.onmousedown = (e) => {
          // 鼠标按下，计算当前元素距离可视区的距离
      const disX = e.clientX - dialogHeaderEl.offsetLeft
      const disY = e.clientY - dialogHeaderEl.offsetTop

          // 获取到的值带px 正则匹配替换
      let styL, styT

          // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
      if (sty.left.includes('%')) {
        styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100)
        styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100)
      } else {
        styL = +sty.left.replace(/\px/g, '')
        styT = +sty.top.replace(/\px/g, '')
      };

      document.onmousemove = function (e) {
              // 通过事件委托，计算移动的距离
        const l = e.clientX - disX
        const t = e.clientY - disY

              // 移动当前元素
        dragDom.style.left = `${l + styL}px`
        dragDom.style.top = `${t + styT}px`

              // 将此时的位置传出去
              // binding.value({x:e.pageX,y:e.pageY})
      }

      document.onmouseup = function (e) {
        document.onmousemove = null
        document.onmouseup = null
      }
    }
  }
})

// v-dialogDragWidth: 弹窗宽度拖大 拖小
Vue.directive('dialogDragWidth', {
  bind (el, binding, vnode, oldVnode) {
    const dragDom = binding.value.$el.querySelector('.el-dialog')

    el.onmousedown = (e) => {
          // 鼠标按下，计算当前元素距离可视区的距离
      const disX = e.clientX - el.offsetLeft

      document.onmousemove = function (e) {
        e.preventDefault() // 移动时禁用默认事件

              // 通过事件委托，计算移动的距离
        const l = e.clientX - disX
        dragDom.style.width = `${l}px`
      }

      document.onmouseup = function (e) {
        document.onmousemove = null
        document.onmouseup = null
      }
    }
  }
})
