package com.spring.cloud.ms.common.vo;

import org.springframework.http.HttpStatus;

public class ResultMessage<T> {

    private HttpStatus httpStatus = null;
    private T Body = null;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getBody() {
        return Body;
    }

    public void setBody(T body) {
        Body = body;
    }
}
