/**
 * 参考：
 * https://zhouhao.me/2018/03/19/format-vue-with-prettier-in-vscode/
 * http://www.jk-kj.com/2017/11/03/vscode%E9%85%8D%E7%BD%AEeslint/
 */
module.exports = {
  root: true,
  env: {
    browser: true,
    es6: true
  },
  'extends': [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'space-before-function-paren': 0,
    // 关闭语句强制分号结尾
    'semi': [0]
  },
  parserOptions: {
    parser: 'babel-eslint'
  },
  plugins: []
}
