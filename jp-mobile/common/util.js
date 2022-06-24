
/**
 * base64加密
 */
var Base64 = { 
	_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	encode:function(e){
		var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t
		},
}

/**
 * 表单对象赋值:
 * 对目标对象存在且源对象同样存在的属性，全部覆盖；
 * 目标对象不存在但是源对象存在的属性， 全部丢弃；
 * 目标对象存在但是源对象不存在的属性，如果是字符串赋值为空串，其余类型赋值为undefined
 */
function recover (target, source) {
  if (target === undefined || target === null) { throw new TypeError('Cannot convert first argument to object') }
  var to = Object(target)
  if (source === undefined || source === null) { return to }
  var keysArray = Object.keys(Object(target))
  for (var nextIndex = 0, len = keysArray.length; nextIndex < len; nextIndex++) {
    var nextKey = keysArray[nextIndex]
    var desc = Object.getOwnPropertyDescriptor(target, nextKey)
    if (desc !== undefined && desc.enumerable) {
      if (to.hasOwnProperty(nextKey)) {
        if (to[nextKey] instanceof Array) {
          to[nextKey] = source[nextKey]
        } else if (to[nextKey] instanceof Object) {
          recover(to[nextKey], source[nextKey])
        } else if (source[nextKey] !== undefined) {
          to[nextKey] = source[nextKey]
        } else if (typeof (to[nextKey]) === 'string') {
          to[nextKey] = ''
        } else {
          to[nextKey] = undefined
        }
      }
    }
  }
  return to
}

/**
 * 表单对象赋值:
 * 对目标对象存在且源对象同样存在的属性，全部覆盖；
 * 目标对象不存在但是源对象存在的属性， 全部丢弃；
 * 目标对象存在但是源对象不存在的属性，保留目标对象的属性不做处理
 */
function recoverNotNull (target, source) {
  if (target === undefined || target === null) { throw new TypeError('Cannot convert first argument to object') }
  var to = Object(target)
  if (source === undefined || source === null) { return to }
  var keysArray = Object.keys(Object(target))
  for (var nextIndex = 0, len = keysArray.length; nextIndex < len; nextIndex++) {
    var nextKey = keysArray[nextIndex]
    var desc = Object.getOwnPropertyDescriptor(target, nextKey)
    if (desc !== undefined && desc.enumerable) {
      if (to.hasOwnProperty(nextKey)) {
        if (to[nextKey] instanceof Array) {
          to[nextKey] = source[nextKey]
        } else if (to[nextKey] instanceof Object) {
          recover(to[nextKey], source[nextKey])
        } else if (source[nextKey] !== undefined) {
          to[nextKey] = source[nextKey]
        }
      }
    }
  }
  return to
}
module.exports = {
	Base64:Base64,
	recover: recover,
	recoverNotNull: recoverNotNull
}
