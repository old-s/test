package com.demo.ctw.rx;

/**
 * Created by qiu on 2018/9/6.
 */

public class ResultResponse<T>{
    int status;
    String message;
    T object;

    public ResultResponse(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }
}
