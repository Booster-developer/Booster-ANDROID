package com.example.booster.data.datasource.model

data class PopupOptionData(
    val status: Int,
    val success: String,
    val message: String,
    val data: PopupOptionInfo
)

data class PopupOptionInfo(
    var file_color: String,
    var file_direction: String,
    var file_sided_type: String,
    var file_collect: Int,
    var file_copy_number: Int,
    var file_range: String? =null,
    var file_range_max: Int?=null,
    var file_range_min: Int?=null
)