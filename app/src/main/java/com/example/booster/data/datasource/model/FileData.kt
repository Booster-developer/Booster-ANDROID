package com.example.booster.data.datasource.model

import android.net.Uri

data class FileData(
    var name: String? = "",
    var type: String? = "",
    var uri: Uri? = null,
    var option_view: String? = "",
    var option_change: String? = "",
    var file_img: String? = "",
    var delete_img: String? = ""
) {
    constructor(uri: Uri):this (
        "",
        "",
        uri,
        "",
        "",
        "",
        ""
    )
}