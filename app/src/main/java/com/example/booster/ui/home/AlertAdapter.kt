package com.example.booster.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.util.AnimationUtil
import com.example.booster.R
import com.example.booster.data.datasource.model.AlertDataInfo
import com.example.booster.onlyOneClickListener
import kotlinx.android.synthetic.main.item_alert.view.*


class AlertAdapter(private val context: Context,
                   private val clickListener: AlertViewHolder.onClickAlertListener) : RecyclerView.Adapter<AlertViewHolder>(){

    var data = mutableListOf<AlertDataInfo>()
    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_alert, parent, false)
        return AlertViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(data[position]) //data를 통째로 xml에 전달
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position

    }
}

class AlertViewHolder(view: View,
                      val clickListener: onClickAlertListener) : RecyclerView.ViewHolder(view){

    fun bind(alertDataInfo: AlertDataInfo) {
        if(alertDataInfo.notice_confirm==1){ // 1은 미확인 --> 알림 활성화
            itemView.item_alert_img.setImageResource(R.drawable.notice_ic_alarm_active)
        }else{ // 0 읽은 알림
            itemView.item_alert_img.setImageResource(R.drawable.notice_ic_alarm_inactive)
        }
        itemView.item_alert_idx.text = "no. " + alertDataInfo.notice_idx.toString()
        itemView.item_alert_msg.text = alertDataInfo.store_name
        itemView.item_alert_txt1.text = "에서 주문하신 인쇄가 완료되었습니다."
        itemView.item_alert_time.text = alertDataInfo.notice_time
    }

    init {
        itemView.onlyOneClickListener {
            clickListener.onClickAlert(adapterPosition)
        }
    }

    interface onClickAlertListener{
        fun onClickAlert(position: Int)
    }
}