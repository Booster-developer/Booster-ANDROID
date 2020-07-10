package com.example.booster.data.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestLoginToServer {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://52.79.218.88:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestLoginInterface = retrofit.create(
        RequestLoginInterface::class.java)
}