package com.example.booster.data.datasource.model

data class EngineListData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: HistoryResponse
)

data class HistoryResponse(
    val engine_point: Int,
    val engine_list: ArrayList<EngineData>
)

data class EngineData(
    val engine_sign: Int,
    val engine_cost: Int,
    val engine_time: String,
    val engine_store_name: String
)