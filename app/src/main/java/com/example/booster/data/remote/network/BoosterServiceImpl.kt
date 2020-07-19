package com.example.booster.data.remote.network

import com.example.booster.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BoosterServiceImpl {
    private const val BASE_URL = "http://52.79.218.88:3000/"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(CookiesInterceptor())
            .addNetworkInterceptor(CookiesInterceptor()).build()

    //파일 업로드
    private val okHttpClientFileUpload: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()/*.addInterceptor(CookiesInterceptor())*/
                .addInterceptor(interceptor)
                /*.addNetworkInterceptor(CookiesInterceptor())*/.build()
        }

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(
            okHttpClient
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitFileUpload: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(
            okHttpClientFileUpload
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: BoosterService = retrofit.create(BoosterService::class.java)

    val serviceFileUpload: BoosterService = retrofitFileUpload.create(BoosterService::class.java)
}