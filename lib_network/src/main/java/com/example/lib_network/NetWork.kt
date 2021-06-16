package com.example.lib_network

import com.dlg.network.interceptor.GlobalHeaderInterceptor
import com.dlg.network.interceptor.GlobalUrlInterceptor
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  created by ws
 *   on 2021/6/4
 *   describe:
 */
 class NetWork() {

    companion object {

        var url: String = ""
        var baseOkHttpClient: OkHttpClient

        init {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }

            baseOkHttpClient = OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
                .retryOnConnectionFailure(true) // 连接失败后是否重新连接
                .followRedirects(true) // 302重定向
                .addInterceptor(GlobalHeaderInterceptor())
                .addInterceptor(logging)
                .addInterceptor(GlobalUrlInterceptor()).build()
        }
        fun requestData(head: String) : ApiService{
            when (head) {
                "api" -> {
                    url = "https://api2.dalinggong.com/"
                }
                "10" -> {
                    url = "http://10.7.121.179:8000/"
                }
                "cms" -> {
                    url = "https://cms.dalinggong.com"
                }
            }
            return Retrofit
                .Builder()
                .baseUrl(url)
                .client(baseOkHttpClient)
                .addConverterFactory(NullOnEmptyConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
        fun<T> acceptData(observable: Observable<T>): Observable<T> {
            return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        }
    }

}