import $http from './request.js'
const dictListKey = 'dictList'

export function getDictLabel (type, value, defaultLabel) {
  if ((!value && value !== 0) || (!type && type !== 0)) {
    if (defaultLabel !== undefined) {
      return defaultLabel
    } else {
      return '--'
    }
  }
  let dictList = uni.getStorageSync(dictListKey)
  let dicts = dictList[type]
  if (dicts) {
    for (let i = 0; i < dicts.length; i++) {
      if (dicts[i].value && dicts[i].value.toString() === value.toString()) {
        return dicts[i].label
      }
    }
  }
  if (defaultLabel !== undefined) {
    return defaultLabel
  } else {
    return '--'
  }
}

export function getDictValue (type, label, defaultValue) {
  if ((!label && label !== 0) || (!type && type !== 0)) {
    if (defaultValue !== undefined) {
      return defaultValue
    } else {
      return '--'
    }
  }
  let dictList = uni.getStorageSync(dictListKey)
  let dicts = dictList[type]
  if (dicts) {
    for (let i = 0; i < dicts.length; i++) {
      if (dicts[i].label && dicts[i].label.toString() === label.toString()) {
        return dicts[i].value
      }
    }
  }
  if (defaultValue !== undefined) {
    return defaultValue
  } else {
    return '--'
  }
}

export function getDictList (type) {
  let dictList = uni.getStorageSync(dictListKey)
  if (!type && type !== 0) { // 不传参 返回全部字典
    return  dictList
  }
  let dicts = dictList[type]
  return dicts || []
}
export function setDictList(dictList){
	uni.setStorageSync(dictListKey,dictList);
}

export default {getDictLabel, getDictValue, getDictList, setDictList}
