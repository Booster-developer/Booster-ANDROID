package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class HomeRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun getHome() = remoteDataSource.getHome()
}