package com.hl.retrofitrxjavademo.rxhttp;

import android.content.Context;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Rxjava + Retrofit的缓存
 * Created by HL on 2017/9/17/0017.
 */

public class RxRetrofitCache {

    /**
     * @param context      上下文
     * @param fromNetwork
     * @param cacheKey     缓存的Key
     * @param expireTime
     * @param isSave       是否有缓存
     * @param forceRefresh 是否强制刷新
     * @param <T>
     * @return
     */
    public static <T> Observable<T> load(final Context context, Observable<T> fromNetwork, final String cacheKey
            , final long expireTime, boolean isSave, boolean forceRefresh) {

        Observable<T> fromCache = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                T cache = (T) CacheUtil.readObject(context, cacheKey, expireTime);
                if (cache != null) {
                    e.onNext(cache);
                } else {
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        if (isSave) {
            fromNetwork.map(new Function<T, T>() {
                @Override
                public T apply(T result) throws Exception {
                    //保存缓存
                    CacheUtil.saveObject(context, (Serializable) result, cacheKey);
                    return result;
                }
            });
        }


        if (forceRefresh) {
            return fromNetwork;
        } else {
            return Observable.concat(fromCache, fromNetwork).filter(new Predicate<T>() {
                @Override
                public boolean test(T t) throws Exception {
                    return t != null;
                }
            });
        }
    }
}
