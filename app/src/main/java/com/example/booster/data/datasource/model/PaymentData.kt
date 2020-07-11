package com.example.booster.data.datasource.model

data class PaymentData (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: PaymentInfo
)

data class PaymentInfo(
    val store_name: String,
    val store_idx: Int,
    val fileOption: ArrayList<PaymentFileList>,
    val order_price: Int,
    val user_point: Int,
    val user_remain_point: Int
)

data class PaymentFileList(
    val file_name: String,
    val file_extension: String,
    val file_color: String,
    val file_direction: String,
    val file_sided_type: String,
    val file_collect: Int,
    val file_copy_number: Int,
    val file_price: Int,
    val file_range: String
)