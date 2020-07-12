package com.example.booster.data.repository

import com.example.booster.data.remote.RemoteDataSource
import com.example.booster.data.remote.RemoteDataSourceImpl

class PaymentRepository {
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun getPaymentInfo(orderIdx : Int) = remoteDataSource.getPaymentInfo(orderIdx)
}