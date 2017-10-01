package com.hl.retrofitrxjavademo.rxhttp;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 具有加载对话框的Observer
 * Created by HL on 2017/9/17/0017.
 */

public abstract class RxObserver<T> implements Observer<T> {

    private Context mContext;
    private SpotsDialog mDialog;

    public RxObserver(Context context) {
        mContext = context;
        mDialog = new SpotsDialog(context);
    }

    protected boolean showDialog() {
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (showDialog() && mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) {
            _onError("网络不可用");
        } else if (e instanceof ServerException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }

        if (showDialog() && mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        if (showDialog() && mDialog != null) {
            mDialog.dismiss();
        }
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String message);
}
