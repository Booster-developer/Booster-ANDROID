package com.example.booster.data.datasource.model

import android.net.Uri


data class Wait(
    val store_name: String,
    val store_address: String,
    val file_info: ArrayList<File>,
    val order_price: Int
)

data class File(
    val file_idx: Int,
    val file_name: String?,
    val file_extension: String?,
    val file_path: String?,
    val file_uri: Uri? = null,
    var popupOptionInfo: PopupOptionInfo? = null
)

