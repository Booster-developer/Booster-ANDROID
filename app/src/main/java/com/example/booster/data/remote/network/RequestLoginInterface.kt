package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.RequestLogin
import com.example.booster.data.datasource.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestLoginInterface{
    @POST("/users/signin")
    fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>
}