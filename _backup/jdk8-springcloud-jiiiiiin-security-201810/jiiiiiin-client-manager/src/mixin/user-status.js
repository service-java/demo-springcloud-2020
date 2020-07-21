/**
 * 同步用户登录状态的混合器
 * @type {Object}
 */
export default {
  methods: {
    isLogined() {
      return this.$vp.loginState()
    },
    setLoginState(state) {
      this.$vp.modifyLoginState({
        state
      })
    }
  }
}
