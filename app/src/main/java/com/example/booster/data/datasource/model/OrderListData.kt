package com.example.booster.data.datasource.model

data class OrderListData (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Order
)

data class Order(
    val booster_count: Int,
    val order_list: ArrayList<OrderList>
)

data class OrderList(
    val order_idx: Int,
    val order_store_name: String,
    val order_time: String,
    val order_title: String,
    val order_state: Int
)