package com.example.booster.ui.selectStore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booster.R
import com.example.booster.data.datasource.model.Store
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.selectStore.StoreListAdapter.ViewType.*
import kotlinx.android.synthetic.main.item_selectstore_file.view.*

interface StoreListItemClickListener {
    fun onStoreListItemClicked(store: Store, position: Int)
}

class StoreListAdapter(val storeListItemClickListener: StoreListItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewType {
        LATEST, FAVORITE, EXTRA
    }

    private var storeList: List<Store>

    init {
        storeList = ArrayList()
    }
//    companion object {
//        const val LATEST = 1
//        const val FAVORITE = 2
//        const val EXTRA = 3
//    }

    fun submitList(list: List<Store>) {
        storeList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        when(viewType){
//            ViewType.LATEST.ordinal->{
//                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selectstore_file, parent, false))
//            }
//            ViewType.FAVORITE.ordinal->{
//                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selectstore_file, parent, false))
//            }
//            else->{
//                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selectstore_file, parent, false))
//            }
//        }
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_selectstore_file, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
//        return when (position) {
//            0 -> LATEST.ordinal
//            1 -> FAVORITE.ordinal
//            else -> EXTRA.ordinal
//        }
        return storeList[position].type ?: EXTRA.ordinal
    }

    override fun getItemCount(): Int {
        return storeList.count()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val storeItem = storeList[position]
        when (getItemViewType(position)) {
            LATEST.ordinal -> {
                viewHolder.bind(storeItem, LATEST.ordinal)
            }
            FAVORITE.ordinal -> {
                viewHolder.bind(storeItem, FAVORITE.ordinal)
            }
            else -> {
                viewHolder.bind(storeItem, EXTRA.ordinal)
            }
        }
    }

    //    fun checkTypePosition(store: Store): Boolean{
//        val index = storeList.indexOf(store)
//        if(store.type == FAVORITE.ordinal){
//            return index == 1
//        }else if(store.type == EXTRA.ordinal){
//            var favoriteCount=0
//            storeList.forEach{
//                if(it.type == FAVORITE.ordinal){
//                    favoriteCount++
//                }
//            }
//            val extraStartPos = itemCount - 1 - favoriteCount
//            return index == extraStartPos
//        }
//        return false
//    }
    fun getCount(type: Int): Int {
        var count = 0
        storeList.forEach {
            if (it.type == type) {
                count++
            }
        }
        return count
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dp * scale + 0.5f).toInt()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(storeItem: Store, viewType: Int) {

            when (viewType) {
                LATEST.ordinal -> {
                    itemView.act_select_store_file_tv_recent.visibility = View.VISIBLE
                    itemView.act_select_store_file_tv_recent.text = "최근 주문 매장"
                    itemView.item_selectstore_file_divider.visibility = View.VISIBLE
                    //adjust bottom margin
                    val params = itemView.constraintLayout9.layoutParams as ViewGroup.MarginLayoutParams
                    params.bottomMargin = dpToPx(itemView.context, 16)
                    itemView.constraintLayout9.layoutParams = params
                }
                FAVORITE.ordinal -> {
                    itemView.act_select_store_file_tv_recent.visibility = View.VISIBLE
                    itemView.act_select_store_file_tv_recent.text = "즐겨찾기 매장"
                    itemView.item_selectstore_file_divider.visibility = View.VISIBLE

                    if (storeItem.store_idx == -1) { //없을때
                        itemView.item_selectstore_file_tv_no_favorite_stores.visibility = View.VISIBLE
                        itemView.constraintLayout9.visibility = View.INVISIBLE
                    } else { //있을때
                        itemView.item_selectstore_file_tv_no_favorite_stores.visibility = View.GONE
                        itemView.constraintLayout9.visibility = View.VISIBLE

                        itemView.item_selectstore_file_divider.visibility = View.GONE
                        if (bindingAdapterPosition != 1) {
                            itemView.act_select_store_file_tv_recent.visibility = View.GONE
                            if (bindingAdapterPosition == getCount(storeItem.type!!)) {
                                itemView.item_selectstore_file_divider.visibility = View.VISIBLE
                                //adjust bottom margin
                                val params = itemView.constraintLayout9.layoutParams as ViewGroup.MarginLayoutParams
                                params.bottomMargin = dpToPx(itemView.context, 16)
                                itemView.constraintLayout9.layoutParams = params
                            }
                        } else if (bindingAdapterPosition == 1 && bindingAdapterPosition == getCount(storeItem.type!!)){
                            itemView.item_selectstore_file_divider.visibility = View.VISIBLE
                            //adjust bottom margin
                            val params = itemView.constraintLayout9.layoutParams as ViewGroup.MarginLayoutParams
                            params.bottomMargin = dpToPx(itemView.context, 16)
                            itemView.constraintLayout9.layoutParams = params
                        }
                    }
                }
                else -> {
                    itemView.act_select_store_file_tv_recent.visibility = View.VISIBLE
                    itemView.act_select_store_file_tv_recent.text = "매장 모두 보기"
                    val count = getCount(storeItem.type!!)
                    val startIndex = itemCount - count
                    if (bindingAdapterPosition != startIndex) {
                        itemView.act_select_store_file_tv_recent.visibility = View.GONE
                        if (bindingAdapterPosition == itemCount - 1) {
                            itemView.item_selectstore_file_divider.visibility = View.VISIBLE
                            //adjust bottom margin
                            val params = itemView.constraintLayout9.layoutParams as ViewGroup.MarginLayoutParams
                            params.bottomMargin = dpToPx(itemView.context, 16)
                            itemView.constraintLayout9.layoutParams = params
                        }
                    }
                }
            }

            itemView.item_selectstore_file_tv_storename.text = storeItem.store_name
            itemView.item_selectstore_file_tv_storeaddress.text = storeItem.store_address
            Glide.with(itemView.context).load(storeItem.store_image)
                .into(itemView.item_selectstore_file_iv_storeimg)

            itemView.onlyOneClickListener {
                storeListItemClickListener.onStoreListItemClicked(storeItem, bindingAdapterPosition)
            }
        }
    }
}