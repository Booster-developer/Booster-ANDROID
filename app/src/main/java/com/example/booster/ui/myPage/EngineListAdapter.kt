package com.example.booster.ui.myPage

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.R
import com.example.booster.data.datasource.model.EngineData
import com.example.booster.data.datasource.model.HistoryResponse
import kotlinx.android.synthetic.main.activity_myengine.view.*
import kotlinx.android.synthetic.main.item_engine_list.view.*

class EngineListAdapter(private val context: Context) :
    RecyclerView.Adapter<EngineListViewHolder>() {

    var datas = mutableListOf<EngineData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_engine_list, parent, false)
        return EngineListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: EngineListViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}

class EngineListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(engineData: EngineData) {

        if (engineData.engine_sign == 1) {
            itemView.engine_item_tv_point.text = "+" + engineData.engine_cost.toString() + "P"
            itemView.engine_item_tv_point.setTextColor(Color.parseColor("#467fff"))
        } else {
            itemView.engine_item_tv_point.text = "-" + engineData.engine_cost.toString() + "P"
        }
        itemView.engine_item_tv_date.text = engineData.engine_time
        itemView.engine_item_tv_store.text = engineData.engine_store_name
    }
}