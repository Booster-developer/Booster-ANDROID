package com.example.booster.data.remote

import com.example.booster.data.remote.network.BoosterServiceImpl

class RemoteDataSourceImpl : RemoteDataSource {
    val api = BoosterServiceImpl.service

}