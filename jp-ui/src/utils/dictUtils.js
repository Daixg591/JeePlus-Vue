import DictService from '@/api/sys/DictService'

export function getDictLabel (type, value, defaultLabel) {
  if ((!value && value !== 0) || (!type && type !== 0)) {
    if (defaultLabel !== undefined) {
      return defaultLabel
    } else {
      return '--'
    }
  }
  let dictList = JSON.parse(sessionStorage.getItem('dictList') || '[]')
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
  let dictList = JSON.parse(sessionStorage.getItem('dictList') || '[]')
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
  if (!type && type !== 0) {
    return []
  }
  let dictList = JSON.parse(sessionStorage.getItem('dictList') || '[]')
  let dicts = dictList[type]
  return dicts || []
}

export function refreshDictList () {
  new DictService().getDictMap().then(({data}) => {
    sessionStorage.setItem('dictList', JSON.stringify(data || '[]'))
  })
}

export default {getDictLabel, getDictValue, getDictList, refreshDictList}
