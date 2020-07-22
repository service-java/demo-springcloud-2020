package com.spring.cloud.chapter2.response.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * imports
 **/
public class ResponseUtils {
    /**
     * 获取请求结果响应对象
     *
     * @param data    -- 封装的数据
     * @param status  -- 响应码
     * @param success -- 成功标志
     * @param message -- 响应结果消息
     * @param <T>     -- 封装数据泛型
     * @return HTTP响应实体对象
     */
    public static <T> ResponseEntity<T> generateResponseEntity(
            T data, HttpStatus status, Boolean success, String message) {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("success", success.toString());
        headers.add("message", message);
        ResponseEntity<T> response = new ResponseEntity<>(data, headers, status);
        return response;
    }
}