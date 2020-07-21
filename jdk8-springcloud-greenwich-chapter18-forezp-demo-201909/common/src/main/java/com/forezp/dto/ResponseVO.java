package com.forezp.dto;

import java.io.Serializable;

public class ResponseVO<T> implements Serializable{


    public int code = 0;
    public String error = "";
    public T data;

    public static ResponseVO onSuc(Object data) {
        ResponseVO resp = new ResponseVO();
        resp.data = data;
        return resp;
    }

    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
