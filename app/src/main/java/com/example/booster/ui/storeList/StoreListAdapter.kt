package com.example.booster.ui.storeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.data.datasource.model.StoreListData
import com.example.booster.databinding.ItemStoreSearchBinding

class StoreListAdapter(private val context : Context, private val clickListener : StoreListViewHolder.onClickStoreItemListener) : RecyclerView.Adapter<StoreListViewHolder>(){

    var data = mutableListOf<StoreListData>()
    lateinit var binding : ItemStoreSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        binding = ItemStoreSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreListViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        holder.binding.storeRes = data[position] //data를 통째로 xml에 전달
    }
}

class StoreListViewHolder(val binding : ItemStoreSearchBinding,val clickListener : onClickStoreItemListener) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.itemStoreSearchCl.setOnClickListener {
            clickListener.onClickStoreItem(adapterPosition)
        }
    }

    interface onClickStoreItemListener{
        fun onClickStoreItem(position: Int)
    }
}