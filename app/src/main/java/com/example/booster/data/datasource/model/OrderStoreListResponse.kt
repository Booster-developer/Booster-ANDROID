package com.example.booster.data.datasource.model

data class OrderStoreListResponse(
    val status:Int,
    val success:Boolean,
    val message:String,
    val data: StoreList
)

data class StoreList(
    val recent_order_store: StoreList?,
    val favorite_store:ArrayList<StoreList>?,
    val store_all:ArrayList<StoreList>
)

data class Store(
    val store_idx :Int,
    val store_name :String,
    val store_image:String,
    val store_address:String
)

