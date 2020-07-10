package com.example.booster.data.datasource.model

data class FileResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Wait

)

data class Wait(
    val store_name: String,
    val store_address: String,
    val file_info: ArrayList<File>,
    val order_price: Int
)

data class File(
    val file_idx: Int,
    val file_name: String,
    val file_extension: String,
    val file_path: String
)