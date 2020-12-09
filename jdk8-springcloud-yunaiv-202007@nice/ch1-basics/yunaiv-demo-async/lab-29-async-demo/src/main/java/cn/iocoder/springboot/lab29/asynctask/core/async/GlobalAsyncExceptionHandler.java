package cn.iocoder.springboot.lab29.asynctask.core.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        // 接入 ELK的话，就可以基于该异常日志进行告警
        logger.error("[handleUncaughtException][method({}) params({}) 发生异常]",
                method, params, ex);
    }

}
