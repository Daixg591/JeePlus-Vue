/**
 * 用于定义公用的方法,定义全局参数
 * 
 * 
 */
// 全局变量
const globalData = {
	
}

// 格式化时间
const formatDate = function(time, cFormat) {
	if (arguments.length === 0) {
		return null
	}
	if (!time) {
		return '';
	}

	let fmt = cFormat || 'yyyy-MM-dd HH:mm:ss';

	let date;
	if (typeof time === 'object') {
		date = time;
	} else if (typeof time === 'string') {
		time = time.replace(/-/g,'/');			// 为了兼容ios,ios只能转换2019/12/12这种时间格式
		date = new Date(time);
	} else {
		date = new Date(parseInt(time));
	}

	var o = {
		"M+": date.getMonth() + 1, //月份
		"d+": date.getDate(), //日
		"h+": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时
		"H+": date.getHours(), //小时
		"m+": date.getMinutes(), //分
		"s+": date.getSeconds(), //秒
		"q+": Math.floor((date.getMonth() + 3) / 3), //季度
		"S": date.getMilliseconds() //毫秒
	};
	var week = {
		"0": "\u65e5",
		"1": "\u4e00",
		"2": "\u4e8c",
		"3": "\u4e09",
		"4": "\u56db",
		"5": "\u4e94",
		"6": "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") +
			week[date.getDay() + ""]);
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

// 获取随机数
const getUuid = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
    })
}

// ajax错误处理
const catchError = function(error) {
    let _this = this;
    if (error.response) {
      switch (error.response.status) {
        case 400:
          _this.$message({
            message: error.response.data.msg || '请求参数异常',
            type: 'error'
          });
          break;
        case 401:
        //   sessionStorage.removeItem('user');
          _this.$message({
            message: error.response.data.msg || '密码错误或账号不存在！',
            type: 'warning',
            onClose: function(){
              location.reload();
            }
          });
          break;
        case 403:
          _this.$message({
            message: error.response.data.msg || '无访问权限，请联系企业管理员',
            type: 'warning'
          });
          break;
        default:
          _this.$message({
            message: error.response.data.msg || '服务端异常，请联系技术支持',
            type: 'error'
          });
      }
    };
}

export default {
	globalData,			// 全局变量
	formatDate,			// 格式化时间
	getUuid,			// 获取uuid
	catchError,			// ajax错误处理
}