package com.example.booster.data.remote.network


import com.example.booster.data.datasource.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface BoosterService {

    @GET("/stores/{store_idx}/detail")
    fun getStoreDetail(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreDetailData>


    @GET("stores/university")
    suspend fun getUniversities(): ApiWrapper<List<University>>

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

    @GET("/orders/{order_idx}/list")
    fun getFileList(
        @Path("order_idx") orderIdx: Int
    ): Call<FileResponse>
}

