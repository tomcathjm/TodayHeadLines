package com.todayheadlines.httpapi;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by huang on 2016/12/22.
 */

public class RetrofitBase {

    // 服务器请求根路径
    private static final String BASE_URL = "http://www.imooc.com/api/";

    //  缓存的文件位置
    private static final String HTTP_CACHE_FILENAME = "android/cache";


    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitBase.class) {
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .client(initOkHttpClient())
                            .build();
                    return retrofit;
                }else{
                    return retrofit;
                }
            }
        }else{
            return retrofit;
        }
    }

    public static OkHttpClient mOkHttpClient;

    /**
     * 初始化 okhttp
     *
     * @return
     */
    public static OkHttpClient initOkHttpClient() {

        if (null == mOkHttpClient) {

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                    .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(10, TimeUnit.SECONDS);//设置写入超时时间
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), HTTP_CACHE_FILENAME);
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
            builder.addInterceptor(interceptor);  //添加拦截器


//            if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                builder.addInterceptor(logging);
//            }

            mOkHttpClient = builder.build();
        }

        return mOkHttpClient;
    }

    /**
     * 拦截器
     */
    private static Interceptor interceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            // Do anything with response here

            return response;


//            对head处处理
//            Request interRequest = chain.request().newBuilder()
//                    .addHeader("user-agent", "ejz-app-android")
//                    .addHeader("version", TextUtils.isEmpty(CommenTools.getVersion(EJobApplication.getContext())) ? "4.1.0" :
//                            CommenTools.getVersion(EJobApplication.getContext()))
//                    .build();
//            return chain.proceed(interRequest);

        }
    };
}
