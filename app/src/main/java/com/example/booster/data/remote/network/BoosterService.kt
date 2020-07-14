package com.example.booster.data.remote.network


import com.example.booster.data.datasource.model.*
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BoosterService {

    @GET("/stores/{store_idx}/detail")
    fun getStoreDetail(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreDetailData>


    @POST("address")
    suspend fun uploadFiles(
        @Header("token") token: String,
        @Part("order_comment") orderComment: String,
        @Part fileList: List<File>
    ): ApiWrapper<String?>


    @PUT("/stores/{store_idx}/favorite")
    fun putStoreFav(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreFavData>

    @GET("/progress/list")
    fun getOrderList(
    ): Observable<OrderListData>

    @POST("/users/idcheck")
    fun requestCheckId(
        @Body body: JsonObject
    ): Call<JoinData>

    @POST("/users/signup")
    fun requestJoin(
        @Body body: JsonObject
    ): Call<JoinData>

    @POST("/users/signin")
    fun requestLogin(
        @Body body: JsonObject
    ): Call<LoginData>

    @GET("/orders/{order_idx}/list")
    suspend fun getFileList(
        @Header("token") token: String,
        @Path("order_idx") orderIdx: Int
    ): ApiWrapper<Wait>

    @GET("/orders/{file_idx}/options")
    suspend fun getPopupOption(
        @Header("token") token: String,
        @Path("file_idx") fileIdx: Int
    ): ApiWrapper<PopupOptionInfo>

    @POST("/orders/{file_idx}/options")
    fun changeOption(
        @Path("file_idx") fileIdx: Int,
        @Body() body: JsonObject
    ): Call<DefaultData>

    @GET("/orders/{order_idx}/payment")
    fun getPaymentInfo(
        @Path("order_idx") orderIdx: Int
    ): Observable<PaymentData>

    @PUT("/progress/{order_idx}/pickup")
    fun putPickUp(
        @Path("order_idx") orderIdx: Int
    ): Observable<DefaultData>

    @GET("/progress/{order_idx}/list")
    fun getOrderDetail(
        @Path("order_idx") orderIdx: Int
    ): Call<OrderDetailData>

  @GET("/stores/{univ_idx}/list")
    fun getStoreList(
        @Path("univ_idx") univIdx: Int
    ): Observable<StoreListData>

    @Multipart
    @POST("/orders/{order_idx}/file")
    suspend fun postUploadFile(
        @Header("token") token: String,
        @Path("order_idx") orderIdx: Int,
        @Part file: MultipartBody.Part?
    ): ApiWrapper<com.example.booster.data.datasource.model.File>

    @GET("/stores/list")
    suspend fun getStoreListByJeongRok(
        @Header("token") token: String
    ): ApiWrapper<StoreList>

}