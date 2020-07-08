package com.example.booster.data.remote

import com.example.booster.data.datasource.model.OrderListData
import com.example.booster.data.datasource.model.StoreDetailData
import com.example.booster.data.datasource.model.StoreFavData
import com.example.booster.data.remote.network.BoosterServiceImpl
import io.reactivex.Observable

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
}