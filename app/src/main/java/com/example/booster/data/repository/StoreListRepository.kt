package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class StoreListRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()
    fun getStoreList(univIdx: Int) = remoteDataSource.getStoreList(univIdx)
}