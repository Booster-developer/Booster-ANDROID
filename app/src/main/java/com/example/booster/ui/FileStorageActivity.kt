package com.example.booster.ui

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.FileData
import com.example.booster.ui.fileStorage.FileAdapter
import com.example.booster.ui.fileStorage.MarginItemDecoration
import com.example.booster.util.BoosterUtil
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_DOCS
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_MEDIA
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_DOC
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO
import kotlinx.android.synthetic.main.activity_file_storage.*
import kotlinx.android.synthetic.main.dialog_item_view.*
import kotlinx.android.synthetic.main.my_file.*
import java.io.File


class FileStorageActivity : AppCompatActivity() {

    private val CUSTOM_REQUEST_CODE: Int = 532

    var datas:ArrayList<FileData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_storage)

        rv_file_add.apply {
            layoutManager = LinearLayoutManager(this@FileStorageActivity)
            adapter = FileAdapter(datas, {item, position -> itemDelete(item, position)}, {item, position -> itemOptionChange(item, position)},
                {item, position -> itemOptionView(item, position)})
        }
        rv_file_add.addItemDecoration(
            MarginItemDecoration(
            resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault),
            resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault)
        )
        )
            tv_order.visibility = View.GONE
            tv_cost.visibility = View.GONE
            tv_cost_amount.visibility = View.GONE

    }

    private fun itemOptionChange(item: FileData, position:Int) {
        //옵션 설정 코드 추가
    }

    private fun itemOptionView(item: FileData, position:Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_item_view, null)
        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            .create()
        val dialogclose = view.findViewById<ImageView>(R.id.dial_item_view_close)
        dialogclose.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()
        //alertDialog.window?.setLayout(700, 800)

    }

    private fun itemDelete(item: FileData, position:Int) {
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        val dialogView = layoutInflater.inflate(R.layout.dialog_item_delete, null)
        val textView: TextView = dialogView.findViewById(R.id.dial_item_delete_tv_message)
        textView.text = item.name + "를 삭제하시겠습니까?"
        builder.setView(dialogView)
            .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                datas.remove(item)
                rv_file_add.adapter?.notifyItemRemoved(position)
                if (datas.size == 0) {
                    tv_order.visibility = View.GONE
                    tv_cost.visibility = View.GONE
                    tv_cost_amount.visibility = View.GONE
                }
            }
            .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

            }
            .show()

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        var photoPaths:ArrayList<Uri> = ArrayList()
        var docPaths:ArrayList<Uri> = ArrayList()
        when (requestCode) {
            REQUEST_CODE_PHOTO -> if (resultCode === Activity.RESULT_OK && data != null) {

                photoPaths.addAll(data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_MEDIA))
            }
            REQUEST_CODE_DOC -> if (resultCode === Activity.RESULT_OK && data != null) {

                docPaths.addAll(data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_DOCS))
            }
        }
        addThemToView(photoPaths, docPaths)
    }

    private fun addThemToView(
        imagePaths: ArrayList<Uri>?,
        docPaths: ArrayList<Uri>?
    ) {
        val filePaths: ArrayList<Uri> = ArrayList()
        if (imagePaths != null) {
            for (imguri in imagePaths) {
                val file: FileData = FileData(imguri)
                file.name = BoosterUtil(this).getFileName(imguri)
                file.type = "img"
                datas.add(file)
            }
        }
        if (docPaths != null) {
            for (docuri in docPaths) {
                val file:FileData = FileData(docuri)
                file.name = BoosterUtil(this).getFileName(docuri)
                file.type = BoosterUtil(this).getFileType(docuri)
                datas.add(file)
            }
        }

        if (datas.size >= 1) {
            tv_order.visibility = View.VISIBLE
            tv_cost.visibility = View.VISIBLE
            tv_cost_amount.visibility = View.VISIBLE
        } else {
            tv_order.visibility = View.GONE
            tv_cost.visibility = View.GONE
            tv_cost_amount.visibility = View.GONE
        }

        rv_file_add.adapter?.notifyDataSetChanged()


//        if (recyclerview != null) {
//            val layoutManager =
//                StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
//            layoutManager.gapStrategy =
//                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
//            recyclerView.layoutManager = layoutManager
//            val imageAdapter =
//                ImageAdapter(this, filePaths, object : ImageAdapterListener() {
//                    fun onItemClick(uri: Uri?) {
//                        try {
//                            //make sure to use this getFilePath method from worker thread
//                            val path = getFilePath(recyclerView.context, uri!!)
//                            if (path != null) {
//                                Toast.makeText(recyclerView.context, path, Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        } catch (e: URISyntaxException) {
//                            e.printStackTrace()
//                        }
//                    }
//                })
//            recyclerView.adapter = imageAdapter
//            recyclerView.itemAnimator = DefaultItemAnimator()
//        }
        Toast.makeText(this, "Num of files selected: " + filePaths.size, Toast.LENGTH_SHORT)
            .show()
    }

    fun onClick(view: View) {
        when(view) {
            FileStorage_img_close -> showDeleteDialog()
            iv_file_add -> fileAdd()
        }
    }


    private fun fileAdd() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("추가할 파일의 종류를 선택해주세요")
        builder.setPositiveButton("이미지") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this, REQUEST_CODE_PHOTO);
        }
        builder.setNegativeButton("문서") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickFile(this, REQUEST_CODE_DOC);
        }
        builder.show()

    }


    private fun showDeleteDialog() {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            val dialogView = layoutInflater.inflate(R.layout.dialog_return, null)


            builder.setView(dialogView)
                .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->

                }
                .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

                }
                .show()
    }

}
