package com.example.booster.ui.orderCondition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.data.datasource.model.OrderConditionData
import com.example.booster.databinding.ItemOrderConditionBinding
import com.example.booster.databinding.ItemStoreSearchBinding
import com.example.booster.ui.store.StoreListAdapter

class OrderConditionAdapter (private val context : Context) : RecyclerView.Adapter<OrderConditionAdapter.ViewHolder>(){

    var data = mutableListOf<OrderConditionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderConditionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.conditionRes = data[position] //data를 통째로 xml에 전달
    }

    inner class ViewHolder(val binding : ItemOrderConditionBinding) : RecyclerView.ViewHolder(binding.root)
}