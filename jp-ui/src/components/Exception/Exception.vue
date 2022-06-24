<template>
  <div
    class="exception"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <div class="img-block">
      <div
        class="img-ele"
        :style="{backgroundImage: `url(${img || config[pageType].img})`}"
      />
    </div>
    <div class="content">
      <h1>{{title || config[pageType].title}}</h1>
      <div class="desc">{{desc || config[pageType].desc}}</div>
      <div class="actions">
        <slot
          v-if="$slots.actions" 
          name="actions"
        />
          <el-button type="primary" @click="goHome">返回首页</el-button>
      </div>
    </div>
  </div>
</template> 

<script lang="ts">
import config from './typeConfig'

export default {
  props: {
    type: {
      type: String,
      default: '404'
    },
    title: String,
    desc: String,
    img: String
  },
  data () {
    return {
      config: config
    }
  },
  methods: {
    goHome () {
      this.$store.dispatch('tagsView/delView', {fullPath: this.$route.fullPath})
      this.$router.push('/')
    }
  },
  computed: {
    pageType () {
      return this.type in this.config ? this.type : '404'
    }
  }
}
</script>

<style lang="scss" scoped>

.exception {
  display: flex;
  align-items: center;
  height: 100%;

  .img-block {
    flex: 0 0 62.5%;
    width: 62.5%;
    padding-right: 152px;
    zoom: 1;
    &:before,
    &:after {
      content: " ";
      display: table;
    }
    &:after {
      clear: both;
      visibility: hidden;
      font-size: 0;
      height: 0;
    }
  }

  .img-ele {
    height: 360px;
    width: 100%;
    max-width: 430px;
    float: right;
    background-repeat: no-repeat;
    background-position: 50% 50%;
    background-size: contain;
  }

  .content {
    flex: auto;

    h1 {
      color: #434e59;
      font-size: 72px;
      font-weight: 600;
      line-height: 72px;
      margin-bottom: 24px;
    }

    .desc {
      color: transparentize(#000, 0.55);
      font-size: 20px;
      line-height: 28px;
      margin-bottom: 16px;
    }

    .actions {
      button:not(:last-child) {
        margin-right: 8px;
      }
    }
  }
}

@media screen and (max-width: 1200px) {
  .exception {
    .img-block {
      padding-right: 88px;
    }
  }
}

@media screen and (max-width: 576px) {
  .exception {
    display: block;
    text-align: center;
    .img-block {
      padding-right: 0;
      margin: 0 auto 24px;
    }
  }
}

@media screen and (max-width: 480px) {
  .exception {
    .img-block {
      margin-bottom: -24px;
      overflow: hidden;
    }
  }
}
</style>

