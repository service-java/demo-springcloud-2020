module.exports = {
  proxy: {
    '/mng': {
      target: 'http://127.0.0.1:9090',
      pathRewrite: {
        '^/mng': '/mng'
      },
      changeOrigin: true
    }
  },
  open: false,
  quiet: false
}
