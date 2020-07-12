package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class OrderListRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun getOrderList() = remoteDataSource.getOrderList()
    fun putPickUp(orderIdx: Int) = remoteDataSource.putPickUp(orderIdx)
}