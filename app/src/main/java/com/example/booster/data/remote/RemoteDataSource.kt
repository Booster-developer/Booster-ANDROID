package com.example.booster.data.remote

import com.example.booster.data.datasource.model.StoreDetailData
import io.reactivex.Observable


interface RemoteDataSource {
    fun getStoreDetail(storeIdx : Int) : Observable<StoreDetailData>
}