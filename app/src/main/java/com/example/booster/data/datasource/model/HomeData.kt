package com.example.booster.data.datasource.model

data class HomeData(
    val status : Int,
    val success: Boolean,
    val message: String,
    val data: HomeInfo
)

data class HomeInfo(
    val home_state: Int,
    val user_name: String
)