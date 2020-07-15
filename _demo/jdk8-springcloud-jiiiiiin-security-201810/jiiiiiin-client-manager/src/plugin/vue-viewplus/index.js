import router from '@/router/index'
import store from '@/store/index'
import _ from 'lodash'

export const mixinConfig = {
  baseUrl: process.env.VUE_APP_SEVER_URL,
  serverUrl: `${process.env.VUE_APP_SEVER_URL}${process.env.VUE_APP_API}`
}

export default {
  router,
  store,
  debug: process.env.NODE_ENV !== 'production',
  runNative: false,
  errorHandler(err) {
    if (_.isError(err)) {
      if (err.code) {
        switch (err.code) {
          case 'RUN_EVN_NOT_SUPPORT':
          case 'NOT_SUPPORT_AJAX_JSBRIDGE':
            console.debug(err)
            break
          default:
            console.warn(err)
        }
      } else {
        console.error(err.message)
      }
    } else {
      console.error(`err参数错误 ${err}`)
    }
  },
  loginStateCheck: {
    isLogined: false,
    checkPaths: [
      /^(\/admin.+)$/
    ],
    onLoginStateCheckFail(to, from, next) {
      this.toast('您尚未登录，请先登录', {
        type: 'warning'
      })
      next('/login')
    }
  },
  utilHttp: {
    baseURL: mixinConfig.serverUrl,
    headers: {
      Accept: 'application/json'
    },
    // 适配后端`com.baomidou.mybatisplus.extension.api.R`接口
    msgKey: 'msg',
    dataKey: 'data',
    errInfoOutDataObj: true,
    defShowLoading: true,
    onParseServerResp(response) {
      return response.data.code !== 0
    },
    onReqErrParseMsg(response, errMsg) {
      switch (errMsg) {
        case '未解析到服务端返回的错误消息':
          return errMsg
      }
      return `${errMsg} [服务端]`
    },
    errDialog(content = '错误消息未定义') {
      this.dialog(content, {
        title: '错误消息'
      })
      return this
    },
    loading(loadingHintText) {
      this.loading(loadingHintText)
    },
    hideLoading() {
      this.hideLoading()
    },
    accessRules: {
      sessionTimeOut: ['role.invalid_user'],
      onSessionTimeOut(response) {
        this.toast('会话超时，请重新登录', {
          type: 'warning'
        })
        store.commit('logout')
      },
      unauthorized: ['core_error_unauthorized'],
      onUnauthorized(response) {
        console.error(`onUnauthorized ${response}`)
        this.toast('您尚未登录，请先登录', {
          type: 'error'
        })
      }
    }
  }
}
