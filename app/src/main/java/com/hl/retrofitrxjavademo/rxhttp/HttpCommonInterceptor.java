package com.hl.retrofitrxjavademo.rxhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 * Created by HL on 2017/9/17/0017.
 */

public class HttpCommonInterceptor implements Interceptor {

    private Map<String, String> mHeaderParams = new HashMap<>();

    private HttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
//        //添加新的参数
//        HttpUrl.Builder builder = oldRequest.url()
//                .newBuilder()
//                .host(oldRequest.url().host())
//                .scheme(oldRequest.url().scheme());

        Request.Builder builder = oldRequest.newBuilder();
        builder.method(oldRequest.method(), oldRequest.body());

        //添加新的参数
        if (mHeaderParams.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParams.entrySet()) {
                builder.header(params.getKey(), params.getValue());
            }
        }

        Request request = builder.build();
        return chain.proceed(request);
    }

    public static class Builder {

        private HttpCommonInterceptor mInterceptor;

        public Builder() {
            mInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mInterceptor.mHeaderParams.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return mInterceptor;
        }
    }
}
