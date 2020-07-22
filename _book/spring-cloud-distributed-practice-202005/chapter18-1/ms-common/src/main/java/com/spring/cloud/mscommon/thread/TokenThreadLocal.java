package com.spring.cloud.mscommon.thread;
// 让线程保存Token信息
public class TokenThreadLocal {

    // ThreadLocal 声明为String型
    private final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    // 线程变量保存
    public void setToken(String token) {
        tokenThreadLocal.set(token);
    }

    // 获取新城变量
    public String getToken() {
        return tokenThreadLocal.get();
    }

    // 不可实例化
    private TokenThreadLocal() {
    };

    // 单例
    private static TokenThreadLocal ss = null;

    // 创建单例
    public static TokenThreadLocal get() {
        if (ss == null) {
            ss = new TokenThreadLocal();
        }
        return ss;
    }
}