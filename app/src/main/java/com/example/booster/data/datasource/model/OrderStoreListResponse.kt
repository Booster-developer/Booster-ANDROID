package com.example.booster.data.datasource.model


data class StoreList(
    val recent_order_store: Store?,
    val favorite_store:ArrayList<Store>?,
    val store_all:ArrayList<Store>
)

data class Store(
    val store_idx :Int,
    val store_name :String,
    val store_image:String,
    val store_address:String,
    val type: Int ?= null
)

