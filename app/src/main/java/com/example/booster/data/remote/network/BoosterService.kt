package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.StoreDetailData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoosterService {

    @GET("/stores/{store_idx}/detail")
    fun getStoreDetail(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreDetailData>
}