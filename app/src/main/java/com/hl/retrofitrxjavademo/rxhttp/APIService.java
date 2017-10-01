package com.hl.retrofitrxjavademo.rxhttp;

import com.hl.retrofitrxjavademo.bean.Movie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HL on 2017/9/17/0017.
 */

public interface APIService {

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<BaseResponse<List<Movie>>> getTop250(@Query("start") int start, @Query("count") int count);

}
