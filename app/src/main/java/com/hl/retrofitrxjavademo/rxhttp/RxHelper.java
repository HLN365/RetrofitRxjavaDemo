package com.hl.retrofitrxjavademo.rxhttp;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据的预处理，线程的切换
 * Created by HL on 2017/9/17/0017.
 */

public class RxHelper {

    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {

        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {

                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> result) throws Exception {
//                        int status = result.status;
//                        if (result.isSuccess()) {
//                            return createData(result.subjects);
//                        } else {
//                            return Observable.error(new ServerException(result.message));
//                        }

                        if (result != null) {
                            return createData(result.subjects);
                        } else {
                            return Observable.error(new ServerException(result.message));
                        }

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据，并发射出去
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> ObservableSource<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }
}
