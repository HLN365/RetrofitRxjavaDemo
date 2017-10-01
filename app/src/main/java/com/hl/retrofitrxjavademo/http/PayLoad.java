package com.hl.retrofitrxjavademo.http;

import io.reactivex.functions.Function;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class PayLoad<T> implements Function<BaseResponse<T> ,T>{
    @Override
    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
        if(!tBaseResponse.isSuccess()){
            throw new Fault(tBaseResponse.status,tBaseResponse.message);
        }
        return tBaseResponse.data;
    }
}
