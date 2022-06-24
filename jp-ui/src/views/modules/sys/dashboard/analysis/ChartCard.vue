<template>
    <el-card shadow="naver">
        <slot name = "card-head"></slot>
        <slot name = "card-content"></slot>
        <slot name = "card-footer"></slot>
    </el-card>
</template>

<script>
    export default {
      name: 'CardChart'
    }
</script>
<style scoped>
        .el-card__body {
            padding: 10px !important;
        }
</style>