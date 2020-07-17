package com.example.booster.data.datasource.model

data class StoreListData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data : ArrayList<StoreListInfo>
)

data class StoreListInfo(
    val store_idx: Int,
    val store_name: String,
    val store_image: String,
    val store_location: String,
    val price_color_double: Int,
    val price_color_single: Int,
    val price_gray_double: Int,
    val price_gray_single: Int,
    val store_x_location: Double,
    val store_y_location: Double,
    val store_double_sale: String,
    val store_favorite: Int,
    val store_open: Int
)