package com.example.booster.ui.orderList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.AnimationUtil
import com.example.booster.data.datasource.model.OrderList
import com.example.booster.databinding.ItemOrderListBinding
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.storeList.StoreListViewHolder

class OrderListAdapter (private val context : Context,
                        private val clickPickUp : OrderListViewHolder.onClickPickUpListener) : RecyclerView.Adapter<OrderListViewHolder>(){

    var data = mutableListOf<OrderList>()
    var previousPosition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding = ItemOrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding, clickPickUp)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.binding.orderListRes = data[position] //data를 통째로 xml에 전달

        if(position > previousPosition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPosition = position

        val status = holder.binding.orderListRes!!.order_state
        if(status==1 || status==2){
            holder.binding.itemOrderListTvDonePick.isClickable = false
        }
    }
}

class OrderListViewHolder(val binding : ItemOrderListBinding,
                          val clickPickUp: onClickPickUpListener) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.itemOrderListTvDonePick.onlyOneClickListener {
            clickPickUp.onClickPickUp(adapterPosition,
                binding.itemOrderListTvDonePick,
            binding.orderListRes!!.order_idx)
        }
    }

    interface onClickPickUpListener{
        fun onClickPickUp(position: Int, textView: TextView, orderIdx: Int)
    }
}