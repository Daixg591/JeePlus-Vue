module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  sourceType: 'unambiguous',
  "plugins": [
    [
      "import",
      {
        "libraryName": "vxe-table",
        "style": true // 样式是否也按需加载
      }
    ]
  ]
}
