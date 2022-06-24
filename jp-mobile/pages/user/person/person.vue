<template>
  <view>
    <view class="person-head">
	  <view class="cu-avatar xl round margin-left-sm" @tap="ChooseImage" :style="`background-image:url('${avatar}')`" ></view>

      <view class="person-head-box">
        <view class="person-head-nickname">{{userInfo.name}}</view>
        <view class="person-head-username">ID：{{userInfo.loginName}}</view>
      </view>
    </view>
	<view class="cu-list menu card-menu  margin-top-20">
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type=" fas fa-building"  class="text-blue"  ></Am-FontAwesome>
				<text class="text-grey">公司</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.company && userInfo.company.name || ''}}</text>
			</view>
		</view>
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type="fas fa-sitemap"  class="text-blue"  ></Am-FontAwesome>
				<text class="text-grey">部门</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.office && userInfo.office.name || ''}}</text>
			</view>
		</view>
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type="far fa-address-book"  class="text-blue"  ></Am-FontAwesome>
				<text class="text-grey">岗位</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.post && userInfo.post.name || ''}}</text>
			</view>
		</view>
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type="far fa-user"  class="text-blue"  ></Am-FontAwesome>
				<text class="text-grey">角色</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.roleNames || ''}}</text>
			</view>
		</view>
	</view>
	<view class="cu-list menu card-menu">
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type="fas fa-phone-square-alt" class="text-blue"></Am-FontAwesome>
				<text class="text-grey">联系电话</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.phone || ''}}</text>
			</view>
		</view>
		<view class="cu-item">
			<view class="content">
				<Am-FontAwesome type="fas fa-envelope" class="text-blue"></Am-FontAwesome>
				<text class="text-grey">邮箱</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.email || ''}}</text>
			</view>
		</view>
		<view class="cu-item arrow" @click="toPassword">
			<view class="content">
				<Am-FontAwesome type="fas fa-edit" class="text-red"></Am-FontAwesome>
				<text class="text-grey">修改密码</text>
			</view>
			<view class="action">
				<text class="text-grey">{{userInfo.office.name || ''}}</text>
			</view>
		</view>
	</view>
	<view class="padding-xl">
		<button class="cu-btn block bg-blue margin-tb-sm lg"  @click="outlogin">退出登录</button>
		<view class="cu-tabbar-height"></view>
	</view>

  </view>

</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'
  export default {
	name: "person",
	onShow() {
		this.$auth.checkLogin()
	},
	computed: mapState({
		 userInfo: (state) => state.user.userInfo,
		 avatar: (state) => state.user.avatar
		}),
    methods: {
	  ...mapActions(['refreshUserInfo']),
      /**
       * 修改密码
       */
      toPassword() {
        uni.navigateTo({
          url: '/pages/user/setting/password/password'
        })
      },
	 ChooseImage() {
	 	uni.chooseImage({
	 		count: 4, //默认9
	 		sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
	 		sourceType: ['album'], //从相册选择
	 		success: (res) => {
				this.upload(res.tempFilePaths[0])
	 		}
	 	});
	 },
	 upload(filePath) {
		this.$http.upload('/app/sys/user/imageUpload',filePath).then((res)=>{
			this.refreshUserInfo()
		})
	 },
	  outlogin() {
	  		  this.$http.get('/app/sys/logout?t=' + (new Date()).getTime().toString()).then(({data})=>{
	  		  		this.$store.commit('logout');
					uni.clearStorage();
	  		  		uni.reLaunch({
	  		  			url: '/pages/login/login'
	  		  		})
	  		})
	  }
    }
  }
</script>

<style>
  .person-head {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 150px;
    padding-left: 20px;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .person-head-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    margin-left: 10px;
  }

  .person-head-nickname {
    font-size: 18px;
    font-weight: 500;
    color: #fff;
  }

  .person-head-username {
    font-size: 14px;
    font-weight: 500;
    color: #fff;
  }

  .person-list {
    line-height: 0;
  }
  .cu-list.card-menu {
      overflow: hidden;
      margin-right: 5px;
      margin-left: 5px;
      border-radius: 7px;
  }
  .cu-list.card-menu.margin-top-20 {
      margin-top: -20px;
  }
  .cu-list.menu>.cu-item .content>uni-view:first-child {
      display: -webkit-box;
      display: -webkit-flex;
      display: flex;
      -webkit-box-align: center;
      /* -webkit-align-items: center; */
      /* align-items: center; */
      display: inline-block;
      margin-right: 5px;
      width: 1.6em;
      text-align: center;
  }
</style>
