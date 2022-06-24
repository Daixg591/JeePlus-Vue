const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: "./",
  productionSourceMap: false,
  chainWebpack: (config) => {
    config.resolve.alias.set('@/', resolve('src'))
    // config.plugins.delete('prefetch')
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
        },
        javascriptEnabled: true,
      }
    }
  },

    // 入口设置
    pages: {
      datav: {
        entry: 'src/pages/datav/main.js',
        template: 'src/pages/datav/index.html',
        title: 'datav',
        filename: 'datav.html',
        chunks: ['chunk-vendors', 'chunk-common',  'datav']
      },
      index: {
        entry: 'src/main.js',
        template: 'public/index.html',
        title: 'index.html',
        filename: 'index.html'
      }
    },
  devServer: {
    index: '/index.html', // 运行时，默认打开index页面
    port: 3000,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_SERVER_URL,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/userfiles': {
        target: process.env.VUE_APP_SERVER_URL,
        changeOrigin: true,
        pathRewrite: {
          '^/userfiles': '/userfiles'
        }
      }
    }
  },

  lintOnSave: undefined
}