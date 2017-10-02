package com.hl.retrofitrxjavademo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.hl.retrofitrxjavademo.R;


/**
 * 照片详情
 * Created by HL on 2017/10/2/0002.
 */

public class GankDetailActivity extends Activity {

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_gank_detail);

        //获取图片URL地址
        mUrl = getIntent().getStringExtra("url");

        intView();
    }

    private void intView() {

        final PhotoView photoView = (PhotoView) findViewById(R.id.image);
        //开启图片缩放功能
        photoView.enable();
        //设置缩放类型
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //加载图片
        Glide.with(this).load(mUrl).into(photoView);

        //从图片中获取图片信息
        final Info info = photoView.getInfo();


        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photoView.animaTo(info, new Runnable() {
                    @Override
                    public void run() {
                        GankDetailActivity.this.finish();
                    }
                });
            }
        });

    }
}
