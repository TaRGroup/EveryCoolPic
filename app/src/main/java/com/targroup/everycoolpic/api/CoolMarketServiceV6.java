package com.targroup.everycoolpic.api;

import com.targroup.everycoolpic.model.PictureEntity;
import com.targroup.everycoolpic.model.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: zhoubinjia
 * date: 2017/1/24
 */
interface CoolMarketServiceV6 {

    @GET("picture/list")
    Observable<Result<List<PictureEntity>>> getPictureList(@Query("tag")  String tag, @Query("type")  String type, @Query("page") int page, @Query("firstItem") String firstItem, @Query("lastItem") String lastItem);
}
