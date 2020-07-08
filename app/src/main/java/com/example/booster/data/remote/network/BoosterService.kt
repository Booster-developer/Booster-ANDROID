package com.example.booster.data.remote.network

import com.example.booster.data.datasource.model.OrderListData
import com.example.booster.data.datasource.model.StoreDetailData
import com.example.booster.data.datasource.model.StoreFavData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BoosterService {

    @GET("/stores/{store_idx}/detail")
    fun getStoreDetail(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreDetailData>

    @PUT("/stores/{store_idx}/favorite")
    fun putStoreFav(
        @Path("store_idx") storeIdx: Int
    ): Observable<StoreFavData>

    @GET("/progress/list")
    fun getOrderList(
    ): Observable<OrderListData>
}
