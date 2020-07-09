package com.example.booster.ui.orderList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.AnimationUtil
import com.example.booster.data.datasource.model.OrderList
import com.example.booster.databinding.ItemOrderListBinding

class OrderListAdapter (private val context : Context) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>(){

    var data = mutableListOf<OrderList>()
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.orderListRes = data[position] //data를 통째로 xml에 전달

        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position

    }

    inner class ViewHolder(val binding : ItemOrderListBinding) : RecyclerView.ViewHolder(binding.root)
}