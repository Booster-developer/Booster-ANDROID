package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.RequestJoin
import com.example.booster.data.datasource.model.ResponseJoin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestJoinInterface{
    @POST("/user/signup")
    fun requestJoin(@Body body: RequestJoin): Call<ResponseJoin>
}