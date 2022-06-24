
<template>
 
</template>
<script>
  export default {
    mounted: function () {
      this.casLogin()
    },
    methods: {
  
      // cas登录
      casLogin () {
        let st = this.getTicket()
        let sevice = window.location.protocol + '//' + window.location.host + '/'
        this.$http({
          url: '/sys/casLogin',
          method: 'get',
          params: {'ticket': st, 'service': sevice}
        }).then(({data}) => {
          this.$cookie.set('token', data.token)
          this.$cookie.set('refreshToken', data.refreshToken)
          this.$router.push({name: 'home'})
        })
      },
      getTicket () {
        let url = document.location.toString()
        return url.split('?')[1].split('#')[0].split('=')[1]
      }
    }
  
}
</script>