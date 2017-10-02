package com.hl.retrofitrxjavademo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.hl.retrofitrxjavademo.R;
import com.hl.retrofitrxjavademo.bean.Movie;
import com.hl.retrofitrxjavademo.rxhttp.APIService;
import com.hl.retrofitrxjavademo.rxhttp.BaseResponse;
import com.hl.retrofitrxjavademo.rxhttp.HttpUtil;
import com.hl.retrofitrxjavademo.rxhttp.RetrofitServiceManager;
import com.hl.retrofitrxjavademo.rxhttp.RxObserver;
import com.hl.retrofitrxjavademo.ui.adapter.CardViewItemDecoration;
import com.hl.retrofitrxjavademo.ui.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class MovieActivity extends Activity {

    private final static int STATE_NORMAL = 0;
    private final static int STATE_REFRESH = 1;
    private final static int STATE_LOAD_MORE = 2;
    private int currentState = STATE_NORMAL;

    private int start = 0;
    private int count = 20;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mDatas = new ArrayList<>();
    private MaterialRefreshLayout mRefreshLayout;
    private Toolbar mToolbar;
    private SpotsDialog mSpotsDialog;
    private Disposable mSubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

//        mSpotsDialog = new SpotsDialog(this);
//        mSpotsDialog.show();

        initToolbar();
        initView();
        getData();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieActivity.this.finish();
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview_movie);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refreshlayout);
        mRefreshLayout.setLoadMore(true);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //刷新数据
                start = 0;
                getData();
                currentState = STATE_REFRESH;
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //加载更多数据
                start += count;
                getData();
                currentState = STATE_LOAD_MORE;
            }
        });

        mMovieAdapter = new MovieAdapter(this, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new CardViewItemDecoration());
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    /**
     * 获取数据
     */
    private void getData() {

        Observable<BaseResponse<List<Movie>>> ob = RetrofitServiceManager.getInstance()
                .create(APIService.class).getTop250(start, count);


        HttpUtil.getInstance(this).toSubscribe(ob, new RxObserver<List<Movie>>(this) {

            @Override
            public void _onNext(List<Movie> movies) {
                showData(movies);
            }

            @Override
            public void _onError(String message) {

            }
        },"cacheKey",false,true);


//        MovieLoader movieLoader = new MovieLoader();
//        mSubscribe = movieLoader.getMovie(start, count).subscribe(new Consumer<List<Movie>>() {
//            @Override
//            public void accept(List<Movie> movies) throws Exception {
//                showData(movies);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        });
    }

    /**
     * 显示数据
     */
    private void showData(List<Movie> datas) {

        switch (currentState) {
            case STATE_NORMAL:
                mMovieAdapter.addData(datas);
                break;
            case STATE_REFRESH:
                mMovieAdapter.refreshData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_LOAD_MORE:
                int position = mDatas.size();
                mMovieAdapter.loadMoreData(datas);
                mRecyclerView.scrollToPosition(position);
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
//        mSpotsDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(!mSubscribe.isDisposed()){
////            mSubscribe.dispose();
//        }
    }
}
