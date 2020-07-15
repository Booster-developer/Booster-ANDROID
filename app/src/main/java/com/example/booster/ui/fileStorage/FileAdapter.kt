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

interface FileRecyclerViewOnClickListener{
    fun itemDelete(item: File, position: Int)
    fun itemOptionChange(item: File, position: Int)
    fun itemOptionView(item: File, position: Int)
}

class FileAdapter(
    var fileRecyclerViewOnClickListener: FileRecyclerViewOnClickListener?
//    val itemDelete: (File, Int) -> Unit,
//    val itemOptionChange: (File, Int) -> Unit,
//    val itemOptionView: (File, Int) -> Unit
) :
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private var fileDataList: ArrayList<File>  = ArrayList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.my_file, parent, false)
        return ViewHolder(item)
    }

    var previousPostition = 0
    private val animationUtil = AnimationUtil()

    fun submitList(list: ArrayList<File>?) {
        list?.let{fileList->
            fileDataList.clear()
            fileDataList.addAll(fileList)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return fileDataList.size
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
                fileRecyclerViewOnClickListener?.itemDelete(file, bindingAdapterPosition)
                //itemDelete(file, bindingAdapterPosition)
            }

            itemView.tv_option_change.setOnClickListener {
                fileRecyclerViewOnClickListener?.itemOptionChange(file, bindingAdapterPosition)

                // itemOptionChange(file, bindingAdapterPosition)
            }

            itemView.tv_option_view.setOnClickListener {
                fileRecyclerViewOnClickListener?.itemOptionView(file, bindingAdapterPosition)

                // itemOptionView(file, bindingAdapterPosition)
            }




        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(fileDataList[position])
        if(position > previousPostition ){
            animationUtil.fade_out(holder.itemView)
        }
        previousPostition = position
    }


}