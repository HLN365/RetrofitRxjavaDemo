package com.hl.retrofitrxjavademo.http;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class BaseResponse<T> {

    public int status;
    public String message;
    public T data;

    public boolean isSuccess() {
        return status == 200;
    }

}
