package com.hl.retrofitrxjavademo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.retrofitrxjavademo.R;
import com.hl.retrofitrxjavademo.activity.GankDetailActivity;
import com.hl.retrofitrxjavademo.bean.Gank;
import com.hl.retrofitrxjavademo.ui.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class GankAdapter extends BaseAdapter<Gank, BaseViewHolder> implements BaseAdapter.OnItemClickListener {

    public GankAdapter(Context context, List<Gank> datas) {
        super(context, datas, R.layout.gank_item);
        setOnItemClickListener(this);
    }

    @Override
    public void bindData(BaseViewHolder holder, Gank data) {
        SimpleDraweeView draweeView = holder.getView(R.id.my_image_view);
        TextView des = holder.getView(R.id.tv_desc);
        TextView author = holder.getView(R.id.tv_author);

        draweeView.setImageURI(data.url);
        des.setText(data.desc);
        author.setText(data.who);
    }

    @Override
    public void onItemClick(View view, int position) {
        Gank gank = getItem(position);
        Intent intent = new Intent(mContext, GankDetailActivity.class);
        intent.putExtra("url", gank.url);
        mContext.startActivity(intent);
    }
}
