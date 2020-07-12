package com.example.booster.data.remote

import com.example.booster.data.datasource.model.*
import com.example.booster.data.remote.network.BoosterServiceImpl
import io.reactivex.Observable
import retrofit2.Call

class RemoteDataSourceImpl : RemoteDataSource {
    val api = BoosterServiceImpl.service

    override fun getStoreDetail(storeIdx : Int): Observable<StoreDetailData> =
        api.getStoreDetail(storeIdx)
            .map {
                it }

    override fun putStoreFav(storeIdx: Int): Observable<StoreFavData> =
        api.putStoreFav(storeIdx)
            .map {
                it }

    override fun getOrderList(): Observable<OrderListData> =
        api.getOrderList().map {
            it }

    override fun getPaymentInfo(orderIdx: Int): Observable<PaymentData> =
        api.getPaymentInfo(orderIdx).map{
            it
        }

    override fun putPickUp(orderIdx: Int): Observable<DefaultData> =
        api.putPickUp(orderIdx).map{
            it
        }
}