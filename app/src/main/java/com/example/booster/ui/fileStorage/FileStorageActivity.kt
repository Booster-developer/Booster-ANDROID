package com.example.booster.ui.fileStorage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.*
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.PdfViewerActivity
import com.example.booster.ui.payment.PaymentActivity
import com.example.booster.util.BoosterUtil
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_DOCS
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_DOC
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO
import kotlinx.android.synthetic.main.activity_file_storage.*
import kotlinx.android.synthetic.main.activity_pdf_text.*
import kotlinx.android.synthetic.main.dialog_item_view.view.*
import org.koin.experimental.builder.getArguments
import java.io.IOException


private const val FINISH_SETTING_OPTION = 1000
private const val FINISH_PDF_VIEW = 1001

class FileStorageActivity : AppCompatActivity(), FileRecyclerViewOnClickListener {
    private lateinit var fileStorageViewModel: FileStorageViewModel
    private lateinit var mAdapter: FileAdapter

    private lateinit var docPaths: ArrayList<Uri>
    private lateinit var photoPaths: ArrayList<Uri>

    private var storeIdx: Int = -1
    private var orderIdx: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_storage)
        //photoPaths = ArrayList()
        fileStorage_rv_file_add.apply {
            layoutManager = LinearLayoutManager(this@FileStorageActivity)
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault),
                    resources.getDimensionPixelSize(R.dimen.paddingItemDecorationDefault)
                )
            )
            mAdapter = FileAdapter(this@FileStorageActivity)
            adapter = mAdapter
        }
        fileStorageViewModel = ViewModelProvider(this).get(FileStorageViewModel::class.java)
        subscribeObservers()


        //get intent values
        intent?.let {
            val storeIdx = it.getIntExtra("storeIdx", -1)
            val storeName = it.getStringExtra("storeName")
            val address = it.getStringExtra("storeAddress")
            storeName?.let { name ->
                fileStorage_tv_store_name.text = name
            }
            address?.let { address ->
                fileStorage_tv_store_address.text = address
            }
            if (storeIdx != -1) {
                this.storeIdx = storeIdx

            }
            //처음 대기리스트 들어갔을때 통신
            fileStorageViewModel.getOrderIdx(storeIdx)

            //결제하기
            fileStorage_tv_order.setOnClickListener {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("order_idx",  this.orderIdx)
                Log.e("orderidxfilesto", this.orderIdx.toString())
                startActivity(intent)
            }
        }

    }
        private fun subscribeObservers() {
            fileStorageViewModel.fileLiveData.observe(this, Observer {
                if (it.size == 0) {
                    fileStorage_tv_order.visibility = View.GONE
                    fileStorage_tv_cost.visibility = View.GONE
                    fileStorage_tv_cost_amount.visibility = View.GONE
                } else {
                    fileStorage_tv_order.visibility = View.VISIBLE
                    fileStorage_tv_cost.visibility = View.VISIBLE
                    fileStorage_tv_cost_amount.visibility = View.VISIBLE
                }
                mAdapter.apply {
                    submitList(it)
                }

            })
            fileStorageViewModel.popupOptionLiveData.observe(this, Observer {
                it?.let {
                    showOptionDialog(it)
                }
            })
            fileStorageViewModel.waitlistLiveData.observe(this, Observer {
                it?.let {
                    setWaitList(it)
                }
            })
            fileStorageViewModel.responseMessageLiveData.observe(this, Observer {
                it?.let { errormessage ->
                    Toast.makeText(this, errormessage, Toast.LENGTH_SHORT).show()
                }
            })
            fileStorageViewModel.orderIdxMutableLiveData.observe(this, Observer {
                if (it >= 0) {
                    fileStorageViewModel.getPrice(it)
                    this.orderIdx = it
                }
            })

//        fileStorageViewModel.statusLiveData.observe(this, Observer {
//            if(it == 200){
//                Toast.makeText(this@FileStorageActivity, "Success", Toast.LENGTH_SHORT).show()
//            }else if(it==404){
//                Toast.makeText(this@FileStorageActivity, "ERROR", Toast.LENGTH_SHORT).show()
//            }
//        })
        }

        @SuppressLint("SetTextI18n")
        private fun showOptionDialog(popupOptionInfo: PopupOptionInfo) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_item_view, null)

            // Set item text

            view.dial_item_view_tv_color2.text = "${popupOptionInfo.file_color}"
            view.dial_item_view_tv_orientation2.text = "${popupOptionInfo.file_direction}"
            view.dial_item_view_tv_sided2.text = "${popupOptionInfo.file_sided_type}"
            view.dial_item_view_tv_multiple2.text = "${popupOptionInfo.file_collect} 개"
            view.dial_item_view_tv_number2.text = "${popupOptionInfo.file_copy_number} p"

            if (popupOptionInfo.file_range != "전체 페이지") {
                view.dial_item_view_tv_partial2.text = "${popupOptionInfo.file_range} 부"
            } else view.dial_item_view_tv_partial2.text = "${popupOptionInfo.file_range}"


            val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .create()
            val dialogclose = view.findViewById<ImageView>(R.id.dial_item_view_close)
            dialogclose.onlyOneClickListener {
                alertDialog.dismiss()
            }
            alertDialog.setView(view)
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }

        private fun setWaitList(wait: Wait) {
            fileStorage_tv_cost_amount.text = "${wait.order_price} 원"
        }


        override fun itemOptionChange(item: File, position: Int) {
            val intent = Intent(this@FileStorageActivity, StoreFileOptionActivity::class.java)
            intent.putExtra("fileIdx", item.file_idx)
            intent.putExtra("fileType", item.file_extension)
            Log.e("sent fileType", "check: " + item.file_extension)
            //intent.putExtra("color",item.popupOptionInfo.file_color)
            //intent.put("item", item.popupOptionInfo)  custom object class를 intent로 넘기는 방법 (parcelable)
            startActivityForResult(intent, FINISH_SETTING_OPTION)
        }

        override fun itemOptionView(item: File, position: Int) {
            Log.e("whatis fileIdx", "here: " + item.file_idx)
            fileStorageViewModel.getPopupOption(item.file_idx)


//        val args = Bundle()
//        val fc = fileColor
//        val fd = fileDir
//        val fs = fileSided
//        val fcol = fileCollect
//        val fcopy = fileCopyNum
//        val fr = fileRange
//
//        args.putString("fileColor", fc)
//        args.putString("fileDir", fd)
//        args.putString("fileSided", fs)
//        args.putInt("fileCollect", fcol)
//        args.putInt("fileCopyNum", fcopy)
//        args.putString("fileRange", fr)
//
//        val itemOptionDialog = ItemOptionFragment()
//
//        itemOptionDialog.show(
//            supportFragmentManager, "item option fragment"
//        )
//        itemOptionDialog.arguments = args
//        Log.e("argsss", args.toString())
//
//        requestToServer.service.getPopupOption(
//            2
//        ).enqueue(object :Callback<PopupOptionData>{
//            override fun onFailure(call: Call<PopupOptionData>, t: Throwable) {
//                //통신 실패
//                Log.e("error", t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<PopupOptionData>,
//                response: Response<PopupOptionData>
//            ) {
//                //통신 성공
//                if (response.isSuccessful){
//                    Log.e("통신 성공", response.body().toString())
//                    if(response.body()!!.status==200){
//                        val data = response.body()!!.data
//                        fileColor = data.file_color
//                        fileDir = data.file_direction
//                        fileSided = data.file_sided_type
//                        fileCollect = data.file_collect
//                        fileCopyNum = data.file_copy_number
//                        fileRange = data.file_range!!
//
//                        Log.e("file info -> ", fileColor+ " "+fileDir+ " "+fileSided+ " "+fileCollect+ " "+fileCopyNum+ " "+fileRange)
//                    }
//                }
//            }
//
//        })
        }


        override fun itemDelete(item: File, position: Int) {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            val dialogView = layoutInflater.inflate(R.layout.dialog_item_delete, null)
            val textView: TextView = dialogView.findViewById(R.id.dial_item_delete_tv_message)
            textView.text = item.file_name + "를 삭제하시겠습니까?"
            builder.setView(dialogView)
                .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                    fileStorageViewModel.deleteItem(item)
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

            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    REQUEST_CODE_PHOTO -> {
                        data?.let {
                            photoPaths = ArrayList()
                            val uri =
                                data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
                            uri?.let {
                                photoPaths.addAll(it)
                                addThemToView(true)
                                //showOptionActivity()
                            }
                        }
                    }
                    REQUEST_CODE_DOC -> {
                        data?.let {
                            docPaths = ArrayList()
                            val uri =
                                data.getParcelableArrayListExtra<Uri>(KEY_SELECTED_DOCS)
                            uri?.let {
                                docPaths.addAll(it)
                                addThemToView(false)

                                //showOptionActivity()
                            }
                        }
                    }
                    FINISH_SETTING_OPTION -> {
                        Log.e("chekc storeIdx", "asdfasdfasdfadsf: " + storeIdx)
                        fileStorageViewModel.getOrderIdx(storeIdx)
//                    data?.let {
//                        val color = it.getStringExtra("color")
//                        val direction = it.getStringExtra("direction")
//                        val side = it.getStringExtra("side")
//                        val combine = it.getStringExtra("combine")
//
//                        val range = it.getStringExtra("range")
//
//                        val rangeMin = it.getStringExtra("rangeMin")
//                        val rangeMax = it.getStringExtra("rangeMax")
//
//                        val number = it.getStringExtra("num")
//
//                        val popupOptionInfo = rangeMin?.let {
//                            PopupOptionInfo(
//                                file_color = color,
//                                file_direction = direction,
//                                file_sided_type = side,
//                                file_collect = combine.toInt(),
//                                file_copy_number = number.toInt(),
//                                file_range = range,
//                                file_range_min = rangeMin.toInt(),
//                                file_range_max = rangeMax.toInt()
//                            )
//                        } ?: PopupOptionInfo(
//                            file_color = color,
//                            file_direction = direction,
//                            file_sided_type = side,
//                            file_collect = combine.toInt(),
//                            file_copy_number = number.toInt(),
//                            file_range = range
//                        )
//                        addThemToView()
//                        fileStorageViewModel.setOptions(popupOptionInfo)
//                    }
                    }

                }
            }

        }

        private fun addThemToView(flag: Boolean) {
            val filePaths: ArrayList<Uri> = ArrayList()
            if (flag) {
                for (imgUri in photoPaths) {
                    val filePath = BoosterUtil().getPathFromUri(imgUri)
                    val fileName = BoosterUtil().getFileName(imgUri)
                    val fileType = BoosterUtil().getFileType(filePath!!)
                    val file = File(-1, fileName, fileType, filePath, imgUri)
                    Log.e("check", file.file_name + " " + file.file_extension)
//                file.name = BoosterUtil(this).getFileName(imguri)
//                file.type = "img"
                    fileStorageViewModel.addItem(file)
                    fileStorageViewModel.order(orderIdx)
                }
            } else if (!flag) {
                for (docUri in docPaths) {
                    val filePath = BoosterUtil().getPathFromUri(docUri)
                    val fileName = BoosterUtil().getFileName(docUri)
                    val fileType = BoosterUtil().getFileType(filePath!!)
                    val file = File(-1, fileName, fileType, filePath, docUri)
//                file.name = BoosterUtil(this).getFileName(docuri)
//                file.type = BoosterUtil(this).getFileType(docuri)
                    fileStorageViewModel.addItem(file)
                    fileStorageViewModel.order(orderIdx)
                }
            }
            fileStorage_rv_file_add.adapter?.notifyDataSetChanged()
            Toast.makeText(this, "Num of files selected: " + filePaths.size, Toast.LENGTH_SHORT)
                .show()
        }

        fun onClick(view: View) {
            when (view) {
                fileStorage_img_close -> showDeleteDialog()
                fileStorage_iv_file_add -> fileAdd()
                //fileStorage_tv_order -> fileStorageViewModel.order()
            }
        }


        private fun fileAdd() {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(this, R.style.MyAlertDialogStyle2)
            builder.setTitle("추가할 파일의 종류를 선택해주세요")
            builder.setPositiveButton("이미지") { dialogInterface: DialogInterface, i: Int ->
                FilePickerBuilder.instance
                    .setMaxCount(1)
                    .setActivityTheme(R.style.LibAppTheme) //optional
                    .setActivityTitle("이미지 선택")
                    .pickPhoto(this, REQUEST_CODE_PHOTO);
            }
            builder.setNegativeButton("문서") { dialogInterface: DialogInterface, i: Int ->
                FilePickerBuilder.instance
                    .setMaxCount(1)
                    .setActivityTheme(R.style.LibAppTheme) //optional
                    .setActivityTitle("문서 선택")
                    .pickFile(this, REQUEST_CODE_DOC);
            }
            builder.show()

        }


        private fun showDeleteDialog() {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            val dialogView = layoutInflater.inflate(R.layout.dialog_return, null)


            builder.setView(dialogView)
                .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                    setResult(RESULT_OK)
                    finish()
                }
                .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

                }
                .show()
        }


        override fun pdfviewer(item: File, position: Int) {
            val intent = Intent(this@FileStorageActivity, PdfViewerActivity::class.java)
            val file = item.file_path!!
            if (item.file_extension == ".pdf") {
                intent.putExtra("pdffile", file)
                Log.e("path check", "path: " + item.file_path + "java.io.File()=" + file)
            } else if (item.file_extension == ".png" || item.file_extension == ".jpeg" || item.file_extension == ".jpg") {
                intent.putExtra("imgfile", file)
            }
            startActivityForResult(intent, FINISH_PDF_VIEW)
        }

}