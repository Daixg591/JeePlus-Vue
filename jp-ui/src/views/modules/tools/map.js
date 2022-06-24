export function MP (ak) {
  return new Promise(function (resolve, reject) {
    window.init = function () {
      resolve(window.BMap)
    }
    var script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = `http://api.map.baidu.com/api?v=3.0&ak=${ak}&callback=init`
    script.onerror = reject
    document.head.appendChild(script)
  })
}
