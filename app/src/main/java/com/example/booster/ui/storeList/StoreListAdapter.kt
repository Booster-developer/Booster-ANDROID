package com.example.booster.ui.storeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.AnimationUtil
import com.example.booster.data.datasource.model.StoreListData
import com.example.booster.databinding.ItemStoreListBinding

class StoreListAdapter(private val context : Context,
                       private val clickListener : StoreListViewHolder.onClickStoreItemListener,
                       private val clickFavListener: StoreListViewHolder.onclickFavListener) : RecyclerView.Adapter<StoreListViewHolder>(){

    var data = mutableListOf<StoreListData>()
    lateinit var binding : ItemStoreListBinding
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        binding = ItemStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreListViewHolder(binding, clickListener, clickFavListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        holder.binding.storeRes = data[position] //data를 통째로 xml에 전달
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position
    }
}

class StoreListViewHolder(val binding : ItemStoreListBinding,
                          val clickListener : onClickStoreItemListener,
                          val clickFavListener: onclickFavListener) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.itemStoreSearchCl.setOnClickListener {
            clickListener.onClickStoreItem(adapterPosition)
        }
        binding.itemStoreSearchIvFav.setOnClickListener {
            clickFavListener.onClickFav(adapterPosition,binding.itemStoreSearchIvFav)
        }
    }

    interface onClickStoreItemListener{
        fun onClickStoreItem(position: Int)
    }

    interface onclickFavListener{
        fun onClickFav(position: Int,imageView: ImageView)
    }
}