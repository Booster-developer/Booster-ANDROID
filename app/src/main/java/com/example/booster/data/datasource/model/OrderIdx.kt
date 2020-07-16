package com.example.booster.data.datasource.model

data class OrderIdx(
    val status : Int,
    val success: Boolean,
    val message: String,
    val data: orderIdxInt
)

data class orderIdxInt(
    var order_idx: Int
)