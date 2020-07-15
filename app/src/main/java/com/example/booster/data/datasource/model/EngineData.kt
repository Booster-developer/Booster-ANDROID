package com.example.booster.data.datasource.model

data class EngineData(
    val status: String,
    val success: Boolean,
    val message: String,
    val data: MyEngine
)

data class MyEngine(
    val user_point: Int
)