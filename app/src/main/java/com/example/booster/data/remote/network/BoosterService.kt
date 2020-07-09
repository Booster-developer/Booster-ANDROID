package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.ApiWrapper
import com.example.booster.data.datasource.model.StoreDetailData
import com.example.booster.data.datasource.model.University
import io.reactivex.Observable
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
}