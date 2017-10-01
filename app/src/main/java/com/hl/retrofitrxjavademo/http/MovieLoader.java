package com.hl.retrofitrxjavademo.http;

import com.hl.retrofitrxjavademo.bean.Movie;
import com.hl.retrofitrxjavademo.bean.MovieSubject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by HL on 2017/9/14/0014.
 */

public class MovieLoader extends ObjectLoader {

    private Service mMovieService;

    public MovieLoader() {
        mMovieService = RetrofitServiceManager.getInstance().create(Service.class);
    }

    /**
     * 获取电影列表
     *
     * @param start
     * @param count
     * @return
     */
    public Observable<List<Movie>> getMovie(int start, int count) {

        return mMovieService.getTop250(start, count)
                .map(new Function<MovieSubject, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieSubject movieSubject) throws Exception {
                        return movieSubject.getSubjects();
                    }
                })
                .compose(this.<List<Movie>>applySchedulers());


//        return observer(observable.map(new Function<MovieSubject, List<Movie>>() {
//            @Override
//            public List<Movie> apply(MovieSubject movieSubject) throws Exception {
//                return movieSubject.getSubjects();
//            }
//        }));
    }
}
