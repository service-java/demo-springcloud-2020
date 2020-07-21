var baseConfig = {
    baseURL: 'http://localhost:9090/mng',
    // 测试oauth2流程
    // baseURL: 'http://www.pinzhi365.com',
}

var instance = axios.create({
    baseURL: baseConfig.baseURL,
    method: 'post',
    timeout: 30000,
    headers: {
        // 'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Type': 'application/json',
        'Accept': 'application/json, text/plain, */*',
    },
    params: {},
    // paramsSerializer: function (params) {
    //     return Qs.stringify(params, {arrayFormat: 'brackets'})
    // }
})

Vue.prototype['$vp'] = instance