package com.example.booster.ui.fileStorage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booster.AnimationUtil
import com.example.booster.R
import com.example.booster.data.datasource.model.File
import com.example.booster.util.BoosterUtil
import kotlinx.android.synthetic.main.my_file.view.*

class FileAdapter(
    var datas: ArrayList<File>,
    val itemDelete: (File, Int) -> Unit,
    val itemOptionChange: (File, Int) -> Unit,
    val itemOptionView: (File, Int) -> Unit
) :
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.my_file, parent, false)
        return ViewHolder(item)
    }

    var previousPostition = 0
    private val animationUtil = AnimationUtil()


    override fun getItemCount(): Int {
        return datas.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(file: File) {
            //Log.e("uri", file.uri.path.toString())

            Log.e("file", file.file_name + " " + file.file_extension)
            if (file.file_extension == "img") {
                Glide.with(itemView.context).load(file.file_path).into(itemView.iv_file)
            } else {
                val fileImage = BoosterUtil(itemView.context).getFileImage(file.file_extension)
                Glide.with(itemView.context).load(fileImage).into(itemView.iv_file)
            }

            itemView.tv_file_name.text = file.file_name

            //itemView.tv_option_view.text = file.option_view //사용하면 옵션보기 텍스트가 안뜸

            itemView.iv_file_delete.setOnClickListener{
                itemDelete(file, bindingAdapterPosition)
            }

            itemView.tv_option_change.setOnClickListener {
                itemOptionChange(file, bindingAdapterPosition)
            }

            itemView.tv_option_view.setOnClickListener {
                itemOptionView(file, bindingAdapterPosition)
            }




        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position
    }


}