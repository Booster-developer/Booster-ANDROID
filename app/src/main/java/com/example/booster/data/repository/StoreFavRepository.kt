package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class StoreFavRepository{
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun putStoreFav(storeIdx: Int) = remoteDataSource.putStoreFav(storeIdx)
}