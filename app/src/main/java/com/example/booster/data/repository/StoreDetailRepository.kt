package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class StoreDetailRepository (){
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun getStoreDetail(storeIdx: Int) = remoteDataSource.getStoreDetail(storeIdx)
}