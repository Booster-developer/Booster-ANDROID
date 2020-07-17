package com.example.booster.data.datasource.model

data class ProfileData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: MyData
)

data class MyData(
    val user_name: String,
    val univ_idx: Int,
    val user_id: String,
    val user_point: Int
)