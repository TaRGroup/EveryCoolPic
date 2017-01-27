package com.targroup.everycoolpic.api;

import android.os.Build;
import android.util.Log;

import com.coolapk.market.util.AuthUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.targroup.everycoolpic.exception.ClientException;
import com.targroup.everycoolpic.util.Constant;
import com.targroup.everycoolpic.util.Utils;
import com.targroup.everycoolpic.model.PictureEntity;
import com.targroup.everycoolpic.model.Result;

import java.io.IOException;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bjzhou on 14-7-29.
 */
public class ApiManager {

    private static final String TAG = "ApiManager";

    private static ApiManager instance;
    private CoolMarketServiceV6 mServiceV6;

    private ApiManager() {
        initServiceV6();
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private void initServiceV6() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    Request original = chain.request();

                    Log.d(TAG, "url: " + original.url());

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("User-Agent", Utils.getUserAgent())
                            .header("X-Requested-With", "XMLHttpRequest")
                            .header("X-Sdk-Int", String.valueOf(Build.VERSION.SDK_INT))
                            .header("X-Sdk-Locale", Utils.getLocaleString())
                            .header("X-App-Id", "coolmarket")
                            .header("X-App-Token", AuthUtils.getAS(Utils.getUUID()))
                            .header("X-App-Version", "7.3")
                            .header("X-App-Code", "1701135")
                            .header("X-Api-Version", "7");

                    Request request = requestBuilder.build();
//                Log.d(TAG, "headers: " + request.headers());
                    return chain.proceed(request);
                } catch (IOException e) {
                    //FIXME: call observer.onError if time out.
                    e.printStackTrace();
                    return null;
                }
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.COOL_MARKET_PREURL_V6)
                .build();

        mServiceV6 = retrofit.create(CoolMarketServiceV6.class);
    }

    public Observable<List<PictureEntity>> pictureList(String type) {
        return mServiceV6.getPictureList("", type, 0, "", "")
                .map(new ResultHandlerV6<List<PictureEntity>>())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class ResultHandlerV6<T> implements Function<Result<T>, T> {

        @Override
        public T apply(Result<T> tResult) throws Exception {
            ClientException exception = tResult.checkResult();
            if (exception != null) {
                throw exception;
            }
            return tResult.getData();
        }
    }
}