package com.hl.retrofitrxjavademo.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.hl.retrofitrxjavademo.ui.adapter.BaseAdapter;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> views;
    private BaseAdapter.OnItemClickListener mListener;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);
        views = new SparseArray<View>();
        mListener = listener;
        //给条目添加点击事件
        itemView.setOnClickListener(this);
    }

    /**
     * 获取各个控件
     *
     * @param viewId 控件ID
     * @param <T>    控件类型(泛型)
     * @return 具体类型的控件
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}
