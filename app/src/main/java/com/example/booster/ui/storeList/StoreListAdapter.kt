package com.example.booster.ui.storeList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.AnimationUtil
import com.example.booster.data.datasource.model.StoreListData
import com.example.booster.databinding.ItemStoreListBinding
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel

class StoreListAdapter(private val context : Context,
                       private val clickListener : StoreListViewHolder.onClickStoreItemListener,
                       private val clickFavListener: StoreListViewHolder.onclickFavListener) : RecyclerView.Adapter<StoreListViewHolder>(){
    private lateinit var viewModel2: StoreDetailViewModel

    var data = mutableListOf<StoreListData>()
    lateinit var binding : ItemStoreListBinding
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
//        viewModel2 = ViewModelProvider(context as StoreListActivity).get(StoreDetailViewModel::class.java)
        viewModel2 = ViewModelProvider(context as BottomTabActivity).get(StoreDetailViewModel::class.java)
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
        binding.itemStoreSearchCl.onlyOneClickListener {
            clickListener.onClickStoreItem(adapterPosition)
        }
        binding.itemStoreSearchIvFav.onlyOneClickListener {
            clickFavListener.onClickFav(adapterPosition,binding.itemStoreSearchIvFav,binding.storeRes!!.store_favorite)
        }
    }

    interface onClickStoreItemListener{
        fun onClickStoreItem(position: Int)
    }

    interface onclickFavListener{
        fun onClickFav(position: Int,imageView: ImageView,fav:Int)
    }
}