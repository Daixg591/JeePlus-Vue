<template>

        <Exception
            type="404"
            :style="{minHeight: 500, height: '80%'}"
          >
          </Exception>
</template>

<script>
  import Exception from '@/components/Exception/Exception'
  export default {
    components: {
      Exception
    }
  }
</script>

<style lang="scss">
  .jp-wrapper.jp-page--not-found {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: hidden;

    .jp-content__wrapper {
      padding: 0;
      margin: 0;
      // background-color: #fff;
    }

    .jp-content {
      position: fixed;
      top: 15%;
      left: 50%;
      z-index: 2;
      padding: 30px;
      text-align: center;
      transform: translate(-50%, 0);
    }

    .not-found-title {
      margin: 20px 0 15px;
      font-size: 10em;
      font-weight: 400;
      color: rgb(55, 71, 79);
    }

    .not-found-desc {
      margin: 0 0 30px;
      font-size: 26px;
      text-transform: uppercase;
      color: rgb(118, 131, 143);

      > em {
        font-style: normal;
        color: #ee8145;
      }

    }
    .not-found-btn-gohome {
      margin-left: 30px;
    }

  }
</style>
