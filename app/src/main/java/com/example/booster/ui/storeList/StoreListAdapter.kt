package com.example.booster.ui.storeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.util.AnimationUtil
import com.example.booster.data.datasource.model.StoreListInfo
import com.example.booster.databinding.ItemStoreListBinding
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel

class StoreListAdapter(private val context : Context,
                       private val clickListener : StoreListViewHolder.onClickStoreItemListener,
                       private val clickFavListener: StoreListViewHolder.onClickFavListener) : RecyclerView.Adapter<StoreListViewHolder>(){
    private lateinit var viewModel2: StoreDetailViewModel

    var data = mutableListOf<StoreListInfo>()
    lateinit var binding : ItemStoreListBinding
    var previousPosition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        viewModel2 = ViewModelProvider(context as BottomTabActivity).get(StoreDetailViewModel::class.java)
        binding = ItemStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreListViewHolder(binding, clickListener, clickFavListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        holder.binding.storeRes = data[position] //data를 통째로 xml에 전달
        if(position > previousPosition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPosition = position
    }
}

class StoreListViewHolder(val binding : ItemStoreListBinding,
                          val clickListener : onClickStoreItemListener,
                          val clickFavListener: onClickFavListener) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.itemStoreListCl.onlyOneClickListener {
            clickListener.onClickStoreItem(adapterPosition, binding.storeRes!!.store_idx)
        }
        binding.itemStoreListIvFav.onlyOneClickListener {
            clickFavListener.onClickFav(adapterPosition, binding.itemStoreListIvFav,
                binding.storeRes!!.store_favorite, binding.storeRes!!.store_idx)
        }
    }

    interface onClickStoreItemListener{
        fun onClickStoreItem(position: Int, storeIdx: Int)
    }

    interface onClickFavListener{
        fun onClickFav(position: Int, imageView: ImageView, fav:Int, storeIdx: Int)
    }
}