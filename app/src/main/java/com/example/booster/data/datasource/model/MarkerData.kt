package com.example.booster.data.datasource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarkerData(
    val latitude: Double?,
    val longitude: Double?,
    val name: String?,
    val idx: Int?
) : Parcelable
