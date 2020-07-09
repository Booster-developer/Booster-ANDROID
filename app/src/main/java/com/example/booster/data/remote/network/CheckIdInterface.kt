package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.RequestCheckId
import com.example.booster.data.datasource.model.RequestJoin
import com.example.booster.data.datasource.model.ResponseJoin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckIdInterface{
    @POST("/user/idcheck")
    fun requestCheckId(@Body body: RequestCheckId): Call<ResponseJoin>
}