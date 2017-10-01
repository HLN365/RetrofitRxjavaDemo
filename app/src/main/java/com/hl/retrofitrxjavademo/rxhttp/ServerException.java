package com.hl.retrofitrxjavademo.rxhttp;

/**
 * 对异常结果进行处理
 */
public class ServerException extends Exception {

    public ServerException(String msg) {
        super(msg);
    }
}
