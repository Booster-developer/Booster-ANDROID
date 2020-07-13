package com.example.booster.ui.orderDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.AnimationUtil
import com.example.booster.R
import com.example.booster.data.datasource.model.OrderOption
import com.example.booster.onlyOneClickListener
import kotlinx.android.synthetic.main.item_order_detail.view.*


class OrderAdapter(private val context: Context,
                   private val clickListener: OrderDetailViewHolder.onClickOrderItemListener) : RecyclerView.Adapter<OrderDetailViewHolder>(){

    var data = mutableListOf<OrderOption>()
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(data[position]) //data를 통째로 xml에 전달
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position

    }
}

class OrderDetailViewHolder( view: View,
    val clickListener: onClickOrderItemListener) : RecyclerView.ViewHolder(view){

    fun bind(orderOption: OrderOption) {
        itemView.tv_order_detail_file_name.text = orderOption.file_name
    }
    init {
        itemView.dial_item_order_detail.onlyOneClickListener {
            clickListener.onClickOrderItem(adapterPosition)
        }
    }

    interface onClickOrderItemListener{
        fun onClickOrderItem(position: Int)
    }

}