package com.hl.retrofitrxjavademo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.retrofitrxjavademo.R;
import com.hl.retrofitrxjavademo.bean.Movie;
import com.hl.retrofitrxjavademo.ui.holder.BaseViewHolder;

import java.util.List;

public class MovieAdapter extends BaseAdapter<Movie, BaseViewHolder> implements BaseAdapter.OnItemClickListener {

    public MovieAdapter(Context context, List<Movie> list) {
        super(context, list, R.layout.movie_item);
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Movie movie = getItem(position);
        Toast.makeText(mContext, movie.title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindData(BaseViewHolder holder, Movie data) {
        SimpleDraweeView draweeView = holder.getView(R.id.my_image_view);
        TextView zhName = holder.getView(R.id.tv_zh_name);
        TextView enName = holder.getView(R.id.tv_en_name);
        TextView time = holder.getView(R.id.tv_time);

        draweeView.setImageURI(data.images.medium);
        zhName.setText(data.title);
        enName.setText(data.original_title);
        time.setText("上映时间：" + data.year);
    }
}
