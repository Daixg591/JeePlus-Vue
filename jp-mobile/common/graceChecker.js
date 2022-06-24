/**
数据验证（表单验证）
来自 grace.hcoder.net 
作者 hcoder 深海
*/
module.exports = {
	error:'',
	check : function (data, rule){
		for(var i = 0; i < rule.length; i++){
			if (!rule[i].checkType){return true;}
			if (!rule[i].name) {return true;}
			if (!rule[i].errorMsg) {return true;}
			let value = data
			
			rule[i].name.split('.').forEach((key)=>{
				value = value[key]
			})
			if (!value) {this.error = rule[i].errorMsg; return false;}
			switch (rule[i].checkType){
				case 'string':
					var reg = new RegExp('^.{' + rule[i].checkRule + '}$');
					if(!reg.test(value)) {this.error = rule[i].errorMsg; return false;}
				break;
				case 'int':
					var reg = new RegExp('^(-[1-9]|[1-9])[0-9]{' + rule[i].checkRule + '}$');
					if(!reg.test(value)) {this.error = rule[i].errorMsg; return false;}
					break;
				break;
				case 'between':
					if (!this.isNumber(value)){
						this.error = rule[i].errorMsg;
						return false;
					}
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (value > minMax[1] || value < minMax[0]) {
						this.error = rule[i].errorMsg;
						return false;
					}
				break;
				case 'betweenD':
					var reg = /^-?[1-9][0-9]?$/;
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (value > minMax[1] || value < minMax[0]) {
						this.error = rule[i].errorMsg;
						return false;
					}
				break;
				case 'betweenF': 
					var reg = /^-?[0-9][0-9]?.+[0-9]+$/;
					if (!reg.test(value)){this.error = rule[i].errorMsg; return false;}
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (value > minMax[1] || value < minMax[0]) {
						this.error = rule[i].errorMsg;
						return false;
					}
				break;
				case 'same':
					if (value != rule[i].checkRule) { this.error = rule[i].errorMsg; return false;}
				break;
				case 'notsame':
					if (value == rule[i].checkRule) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'email':
					var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'phone':
					var reg = /^1[0-9]{10,10}$/;
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'url':
					var reg = /^http[s]?:\/\/.*/
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'zipcode':
					var reg = /^[0-9]{6}$/;
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'reg':
					var reg = new RegExp(rule[i].checkRule);
					if (!reg.test(value)) { this.error = rule[i].errorMsg; return false; }
				break;
				case 'in':
					if(rule[i].checkRule.indexOf(value) == -1){
						this.error = rule[i].errorMsg; return false;
					}
				break;
				case 'notnull':
					if(value == null || value.length < 1){this.error = rule[i].errorMsg; return false;}
				break;
			}
		}
		return true;
	},

	checkFormData : function (data){
		for(i=0; i<data.length; i++){
			let item = data[i]
			let name = item.name
			let value = item.value
			if(!(item.readable && item.writable)){
				continue
			}
			if(item.options.required){
				if(value == null || value.length < 1){
					return name+": "+ (item.options.requiredMessage || '必填项不能为空!')
				}
			}
			if(item.options.dataType  && item.options.dataTypeCheck){
				switch (item.options.dataType ){
					case 'number':
						var reg = /^-?[0-9]+(\.\d+)?$/
						if (!reg.test(value)) {
							return name+": "+ item.options.dataTypeMessage
						}
					break;
					case 'integer':
						if (!(/^0$/.test(value) || /^(-[1-9]|[1-9])[0-9]*$/.test(value))) { 
							return name+": "+ item.options.dataTypeMessage
						}
					break;
					case 'float':
						if (!(/^\d+(\.\d+)?$/.test(value) || /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/)) {
							return name+": "+ item.options.dataTypeMessage
						}
					break;
					case 'url':
						var reg = /^http[s]?:\/\/.*/
						if (!reg.test(value)) {
							return name+": "+ item.options.dataTypeMessage
						}
					break;
					case 'email':
						var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
						if (!reg.test(value)) {
							return name+": "+ item.options.dataTypeMessage
						}
					break;
					case 'hex':
						var reg = /^[0-9a-fA-F]{1,3}$/
						if (!reg.test(value)) {
							return name+": "+ item.options.dataTypeMessage
						}
					break;
				}
			};
			if(item.options.pattern && item.options.patternMessage && item.options.patternCheck){
				var reg = new RegExp(item.options.pattern)
				if (!reg.test(value)) {
					return name+": "+ item.options.patternMessage
				}
			}
		}
		return ''
	},
	isNumber : function (checkVal){
		var reg = /^-?[1-9][0-9]?.?[0-9]*$/;
		return reg.test(checkVal);
	}
}