package com.hl.retrofitrxjavademo.http;

import com.hl.retrofitrxjavademo.bean.Gank;
import com.hl.retrofitrxjavademo.bean.GankResp;
import com.hl.retrofitrxjavademo.http.api.API;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class GankLoader extends ObjectLoader {

    private Service mGankService;

    public GankLoader() {
        mGankService = RetrofitServiceManager.getInstance().create(Service.class);
    }

    public Observable<List<Gank>> getGank(int count,int page){
        return observer(mGankService.getGank(API.GANK_URL)
                .map(new Function<GankResp, List<Gank>>() {
                    @Override
                    public List<Gank> apply(GankResp gankResp) throws Exception {
                        return gankResp.results;
                    }
                }));
    }
}
