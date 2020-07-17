package com.example.booster.ui.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booster.data.datasource.model.PaymentFileList
import com.example.booster.databinding.ItemPaymentFileBinding

class PaymentAdapter (private val context : Context) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>(){

    var data = mutableListOf<PaymentFileList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPaymentFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.paymentInfo = data[position] //data를 통째로 xml에 전달
        holder.binding.itemPaymentFileTvPdf.text = "." + data[position].file_extension
    }

    inner class ViewHolder(val binding : ItemPaymentFileBinding) : RecyclerView.ViewHolder(binding.root)
}