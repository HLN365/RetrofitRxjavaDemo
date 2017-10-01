package com.hl.retrofitrxjavademo.rxhttp;

import com.hl.retrofitrxjavademo.http.api.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit实例化
 * Created by HL on 2017/9/17/0017.
 */

public class RetrofitServiceManager {

    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;

    private static RetrofitServiceManager mManager = null;

    private RetrofitServiceManager() {
        // 配置网络请求参数
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        HttpCommonInterceptor interceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform", "android")
                .addHeaderParams("userToken", "1234343434dfdfd3434")
                .addHeaderParams("userId", "123445")
                .build();
        builder.addInterceptor(interceptor);

        //创建 Retrofit 实例
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .build();
    }


    /**
     * 网络请求接口实例
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public static RetrofitServiceManager getInstance() {
        if (mManager == null) {
            mManager = new RetrofitServiceManager();
        }
        return mManager;
    }
}
