package com.example.booster.data.remote.network

import com.example.booster.CookiesIntercepter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BoosterServiceImpl {
    private const val BASE_URL = "http://52.79.218.88:3000/"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(CookiesIntercepter())
            .addNetworkInterceptor(CookiesIntercepter()).build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(
            okHttpClient
        )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: BoosterService = retrofit.create(BoosterService::class.java)
}