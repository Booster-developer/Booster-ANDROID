package com.example.booster.ui.store

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.data.datasource.model.StoreListData
import com.example.booster.databinding.ItemStoreSearchBinding

class StoreListAdapter(private val context : Context) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>(){

    var data = mutableListOf<StoreListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoreSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.storeRes = data[position] //data를 통째로 xml에 전달
    }

    inner class ViewHolder(val binding : ItemStoreSearchBinding) : RecyclerView.ViewHolder(binding.root)
}