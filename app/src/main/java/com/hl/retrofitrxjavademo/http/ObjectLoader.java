package com.hl.retrofitrxjavademo.http;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 * Created by HL on 2017/9/14/0014.
 */

public class ObjectLoader {

    protected <T> Observable<T> observer(Observable<T> observable) {

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //线程转换器
    private ObservableTransformer mTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 线程切换
     *
     * @param <T>
     * @return
     */
    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) mTransformer;
    }
}
