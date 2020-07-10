package com.example.booster.data.datasource.model

data class PopupOptionData(
    val status: Int,
    val success: String,
    val message: String,
    val data: PopupOptionInfo
)

data class PopupOptionInfo(
    val file_color: String,
    val file_direction: String,
    val file_sided_type: String,
    val file_collect: Int,
    val file_copy_number: Int,
    val file_range: String
)