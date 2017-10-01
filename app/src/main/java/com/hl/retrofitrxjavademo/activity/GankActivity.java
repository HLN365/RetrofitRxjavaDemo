package com.hl.retrofitrxjavademo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.hl.retrofitrxjavademo.R;
import com.hl.retrofitrxjavademo.bean.Gank;
import com.hl.retrofitrxjavademo.http.GankLoader;
import com.hl.retrofitrxjavademo.ui.adapter.GankAdapter;
import com.hl.retrofitrxjavademo.ui.adapter.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.functions.Consumer;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class GankActivity extends Activity {

    private final static int STATE_NORMAL = 0;
    private final static int STATE_REFRESH = 1;
    private final static int STATE_LOAD_MORE = 2;
    private int currentState = STATE_NORMAL;


    private RecyclerView mRecyclerView;
    private GankAdapter mGankAdapter;
    private ArrayList<Gank> mDatas = new ArrayList<>();
    private Toolbar mToolbar;
    private int count = 20;
    private int page = 1;
    private MaterialRefreshLayout mRefreshLayout;
    private SpotsDialog mSpotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);

        mSpotsDialog = new SpotsDialog(this);
        mSpotsDialog.show();

        initToolbar();
        initView();
        getData();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GankActivity.this.finish();
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview_gank);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refreshlayout);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                page = 1;
                getData();
                currentState = STATE_REFRESH;
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //加载更多数据
                page++;
                getData();
                currentState = STATE_LOAD_MORE;
            }
        });

        mGankAdapter = new GankAdapter(this, mDatas);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mGankAdapter);
    }

    /**
     * 获取数据
     */
    private void getData() {
        GankLoader gankLoader = new GankLoader();
        gankLoader.getGank(count, page)
                .subscribe(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(List<Gank> ganks) throws Exception {
                        showData(ganks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 显示数据
     *
     * @param ganks
     */
    private void showData(List<Gank> ganks) {

        switch (currentState) {
            case STATE_NORMAL:
                mGankAdapter.addData(ganks);
                break;
            case STATE_REFRESH:
                mGankAdapter.refreshData(ganks);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_LOAD_MORE:
                int position = mDatas.size();
                mGankAdapter.loadMoreData(ganks);
                mRecyclerView.scrollToPosition(position);
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
        mSpotsDialog.dismiss();
    }
}
