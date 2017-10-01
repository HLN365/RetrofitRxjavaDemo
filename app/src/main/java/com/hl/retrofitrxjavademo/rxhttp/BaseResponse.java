package com.hl.retrofitrxjavademo.rxhttp;

/**
 * 封装服务器返回数据
 * Created by HL on 2017/9/17/0017.
 */

public class BaseResponse<T>{

    public int status;      //状态码 200 400等
    public String message;  //状态说明信息
    public T subjects;          //数据


    public boolean isSuccess() {
        return status == 200;
    }
}
