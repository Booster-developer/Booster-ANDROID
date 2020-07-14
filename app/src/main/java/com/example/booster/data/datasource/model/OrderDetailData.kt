package com.example.booster.data.datasource.model

data class OrderDetailData(
    val status: String,
    val successs: Boolean,
    val message: String,
    val data: OrderDetailInfo
)

data class OrderDetailInfo(
    val order_idx: Int,
    val order_store_name: String,
    val order_state: Int,
    val order_time: String,
    val order_price: Int,
    val order_comment: String,
    val order_file_list: ArrayList<OrderOption>
)

data class OrderOption(
    val file_name: String,
    val file_extension: String,
    val file_price: Int,
    val file_color: String,
    val file_range_start: Int,
    val file_range_end: Int,
    val file_sided_type: String,
    val file_direction: String,
    val file_collect: Int,
    val file_copy_number:Int
)