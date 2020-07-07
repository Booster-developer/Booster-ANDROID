package com.example.booster.data.datasource.model

data class StoreDetailData(
    val status: Int,
    val success: String,
    val message: String,
    val data: StoreDetail
)

data class StoreDetail(
    val store_image: String,
    val store_name: String,
    val store_address: String,
    val store_location:String,
    val store_time_weekdays: String,
    val store_time_saturday: String,
    val store_time_sunday: String,
    val store_phone_number: String,
    val price_color_double: Int,
    val price_color_single: Int,
    val price_gray_double: Int,
    val price_gray_single: Int,
    val univ_line: Int,
    val store_favorite: Int
)