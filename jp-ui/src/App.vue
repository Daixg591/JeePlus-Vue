<template>
  <transition v-bind:css="false" name="fade">
    <router-view></router-view>
  </transition>
</template>

<script>
  export default {}
</script>
<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

</style>