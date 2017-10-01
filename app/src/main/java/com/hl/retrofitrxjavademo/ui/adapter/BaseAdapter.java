package com.hl.retrofitrxjavademo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hl.retrofitrxjavademo.ui.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    public List<T> mDatas = new ArrayList<T>();
    public Context mContext;
    public int mLayoutId;
    public OnItemClickListener mListener;

    public BaseAdapter(Context context, List<T> datas, int layoutId) {
        mDatas = datas;
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, mLayoutId, null);
        return new BaseViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() <= 0) {
            return 0;
        }
        return mDatas.size();
    }

    /**
     * 获取指定条目的数据
     *
     * @param position 条目的索引
     * @return 指定条目的数据
     */
    public T getItem(int position) {
        if (position >= mDatas.size()) {
            return null;
        }
        return mDatas.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getDatas() {
        if (mDatas == null) {
            return null;
        }
        return mDatas;
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            //通知适配器刷新
            notifyItemRangeChanged(0, mDatas.size());
        }
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<T> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refreshData(List<T> datas) {
        clearData();
        addData(0, datas);
    }

    /**
     * 加载更多数据
     *
     * @param datas
     */
    public void loadMoreData(List<T> datas) {
        addData(mDatas.size(), datas);
    }

    /**
     * 由子类去绑定控件数据
     *
     * @param holder
     * @param data
     */
    public abstract void bindData(BaseViewHolder holder, T data);

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //注意：该接口要为public
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
