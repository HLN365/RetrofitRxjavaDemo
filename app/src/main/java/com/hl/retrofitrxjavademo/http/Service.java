package com.hl.retrofitrxjavademo.http;

import com.hl.retrofitrxjavademo.bean.GankResp;
import com.hl.retrofitrxjavademo.bean.MovieSubject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 请求的接口
 * Created by HL on 2017/9/14/0014.
 */

public interface Service {

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);


    /**
     * @param url
     * @param
     * @param
     * @return
     */
    @GET
    Observable<GankResp> getGank(@Url String url);

//    Observable<GankResp> getGank(@Url String url, @Path("count")int count, @Path("page")int page);
}
