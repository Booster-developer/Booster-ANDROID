package com.example.booster.ui.orderList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.util.AnimationUtil
import com.example.booster.data.datasource.model.OrderList
import com.example.booster.databinding.ItemOrderListBinding
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.item_order_list.view.*

class OrderListAdapter (private val context : Context,
                        private val clickPickUp : OrderListViewHolder.onClickPickUpListener,
                        private val clickDetail: OrderListViewHolder.onClickDetailListener,
                        private val cancel: OrderListViewHolder.onClickCancelListener) : RecyclerView.Adapter<OrderListViewHolder>(){

    var data = mutableListOf<OrderList>()
    var previousPosition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding = ItemOrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding, clickPickUp, clickDetail, cancel)
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
        }else{
            holder.binding.itemOrderListTvDonePick.isClickable = true

        }

    }
}

class OrderListViewHolder(val binding : ItemOrderListBinding,
                          val clickPickUp: onClickPickUpListener,
                          val clickDetail: onClickDetailListener,
                          val cancel: onClickCancelListener) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.itemOrderListTvDonePick.onlyOneClickListener {
            clickPickUp.onClickPickUp(bindingAdapterPosition,
                binding.itemOrderListTvDonePick,
            binding.orderListRes!!.order_idx)
        }
        itemView.item_order_list_detail.onlyOneClickListener{
            clickDetail.onClickDetail(bindingAdapterPosition)
        }
        itemView.item_order_list_cancel.onlyOneClickListener {
            cancel.onCancel(bindingAdapterPosition)
        }
    }

    interface onClickPickUpListener{
        fun onClickPickUp(position: Int, textView: TextView, orderIdx: Int)
    }

    interface onClickDetailListener{
        fun onClickDetail(position: Int)
    }

    interface onClickCancelListener{
        fun onCancel(position: Int)
    }
}