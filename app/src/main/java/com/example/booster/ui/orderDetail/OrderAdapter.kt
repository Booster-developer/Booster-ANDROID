package com.example.booster.ui.orderDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booster.AnimationUtil
import com.example.booster.R
import com.example.booster.data.datasource.model.OrderOption
import com.example.booster.onlyOneClickListener
import kotlinx.android.synthetic.main.item_order_detail.view.*


class OrderAdapter(private val context: Context,
                   private val clickListener: OrderDetailViewHolder.onClickOrderItemListener,
                   private val clickImgListener: OrderDetailViewHolder.onClickImgListener) : RecyclerView.Adapter<OrderDetailViewHolder>(){

    var data = mutableListOf<OrderOption>()
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailViewHolder(view, clickListener, clickImgListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(data[position])
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position

    }
}

class OrderDetailViewHolder( view: View,
    val clickListener: onClickOrderItemListener,
    val clickImgListener: onClickImgListener) : RecyclerView.ViewHolder(view){

    fun bind(orderOption: OrderOption) {
        Glide.with(itemView.context).load(orderOption.file_thumbnail_path).into(itemView.iv_order_detail)
        itemView.tv_order_detail_file_name.text = orderOption.file_name + "." + orderOption.file_extension
        itemView.tv_order_detail_file_price.text = orderOption.file_price.toString() + "원"
    }
    init {
        itemView.dial_item_order_detail.onlyOneClickListener {
            clickListener.onClickOrderItem(adapterPosition)
        }
        itemView.iv_order_detail.onlyOneClickListener {
            clickImgListener.onClickImg(adapterPosition)
        }
    }

    interface onClickOrderItemListener{
        fun onClickOrderItem(position: Int)
    }

    interface onClickImgListener{
        fun onClickImg(position: Int)
    }

}