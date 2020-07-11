package com.example.booster.data.remote

import com.example.booster.data.datasource.model.*
import io.reactivex.Observable
import retrofit2.Call

interface RemoteDataSource {
    fun getStoreDetail(storeIdx : Int) : Observable<StoreDetailData>
    fun putStoreFav(storeIdx: Int) : Observable<StoreFavData>
    fun getOrderList() : Observable<OrderListData>
    fun getPaymentInfo(orderIdx: Int) : Observable<PaymentData>
}