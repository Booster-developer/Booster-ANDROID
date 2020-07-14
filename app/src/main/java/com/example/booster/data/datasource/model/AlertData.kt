package com.example.booster.data.datasource.model

data class AlertData(
    val status : Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<AlertDataInfo>
)

data class AlertDataInfo(
    val notice_idx: Int,
    val notice_time: String
)