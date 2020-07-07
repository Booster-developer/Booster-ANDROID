package com.example.booster.data.remote

import com.example.booster.data.datasource.model.StoreDetailData
import com.example.booster.data.remote.network.BoosterServiceImpl
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource {
    val api = BoosterServiceImpl.service

    override fun getStoreDetail(storeIdx : Int): Observable<StoreDetailData> =
        api.getStoreDetail(storeIdx)
            .map {
                it }

}