package com.minminaya.data.http;

import android.webkit.WebSettings;

import com.minminaya.data.DataModuleApplication;
import com.minminaya.data.http.api.Urls;
import com.minminaya.data.http.api.ZhihuApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Niwa on 2017/9/28.
 */

public class NetWorkForRestApi {
    private static ZhihuApi zhihuApi;
    private static Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mRxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static ZhihuApi getZhihuApi() {

        if (zhihuApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxJava2CallAdapterFactory)
                    .build();
            zhihuApi = retrofit.create(ZhihuApi.class);
        }
        return zhihuApi;
    }

    /**
     * <p>获取OkHttpCient对象，通过拦截器修改http头部，伪装成浏览器</p>
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .removeHeader("User-Agent")//移除旧的
                                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(DataModuleApplication.getInstance()))//添加真正的头部
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return okHttpClient;
    }


}
