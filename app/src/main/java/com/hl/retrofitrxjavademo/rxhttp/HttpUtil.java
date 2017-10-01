package com.hl.retrofitrxjavademo.rxhttp;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * 把Retrofit和RxJava结合在一起
 * Created by HL on 2017/9/18/0018.
 */

public class HttpUtil {

    private static HttpUtil httpUtil = null;
    private Context mContext;

    private HttpUtil(Context context) {
        mContext = context;
    }

    public static HttpUtil getInstance(Context context) {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                httpUtil = new HttpUtil(context);
            }
        }
        return httpUtil;
    }

    public void toSubscribe(Observable ob, RxObserver observer, String cacheKey, boolean isSave, boolean forceRefresh) {
        //1.数据预处理
        ObservableTransformer<BaseResponse<Object>, Object> result = RxHelper.handleResult();
        //2.重用操作符
        Observable observable = ob.compose(result);
        //3.缓存
        RxRetrofitCache.load(mContext, observable, cacheKey, 0, isSave,forceRefresh).subscribe(observer);
    }
}
