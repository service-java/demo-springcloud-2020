package cn.jiiiiiin.manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理项目中控制器抛出的异常
 *
 * @author jiiiiiin
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    // 自定义响应accept为非html的json响应数据：
    // @ExceptionHandler标识当前接口会处理那个异常
    // 即项目中的控制器，无论那个接口抛出对应的异常，spring都会回调下面的方法来进行异常的处理
    @ExceptionHandler(UsernameNotFoundException.class)
    // 标识需要返回json格式数据
    @ResponseBody
    // 标识返回的状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerUserNotExistException(UsernameNotFoundException e){
        Map<String,Object> res = new HashMap<>();
//        res.put("id", e.getId());
        res.put("message", e.getMessage());
        return res;
    }
}
