package com.example.booster.ui.fileStorage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.File
import com.example.booster.data.datasource.model.PopupOptionInfo
import com.example.booster.data.datasource.model.Wait
import com.example.booster.data.datasource.model.*
import com.example.booster.onlyOneClickListener
import com.example.booster.ui.PdfViewerActivity
import com.example.booster.ui.payment.PaymentActivity
import com.example.booster.util.BoosterUtil
import com.example.booster.util.PDFThumbnailUtils
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_DOCS
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_DOC
import droidninja.filepicker.FilePickerConst.REQUEST_CODE_PHOTO
import kotlinx.android.synthetic.main.activity_file_storage.*
import kotlinx.android.synthetic.main.dialog_item_view.view.*
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList



private const val FINISH_SETTING_OPTION = 1000
private const val FINISH_PDF_VIEW = 1001

class FileStorageActivity : AppCompatActivity(), FileRecyclerViewOnClickListener {
    private lateinit var fileStorageViewModel: FileStorageViewModel
    private lateinit var mAdapter: FileAdapter

    private lateinit var docPaths: ArrayList<Uri>
    private lateinit var photoPaths: ArrayList<Uri>

    private var storeIdx: Int = -1
    private var orderIdx: Int = -1

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(this@FileStorageActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                this@FileStorageActivity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_storage)

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()

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
                intent.putExtra("order_idx", this.orderIdx)
                Log.e("orderidxfilesto", this.orderIdx.toString())
                startActivity(intent)
            }
        }

    }

    override fun onBackPressed() {
        showDeleteDialog()
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
                Log.e("itttt",it.toString())
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
                //fileStorageViewModel.getPrice(it)
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
        view.dial_item_view_tv_number2.text = "${popupOptionInfo.file_copy_number} 부"

        if (popupOptionInfo.file_range != "전체 페이지") {
            view.dial_item_view_tv_partial2.text = "${popupOptionInfo.file_range} p"
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
        Log.e("ccccc",wait.order_price.toString())
        fileStorage_tv_cost_amount.text = "${wait.order_price} 원"
    }


    override fun itemOptionChange(item: File, position: Int) {
        val intent = Intent(this@FileStorageActivity, StoreFileOptionActivity::class.java)
        intent.putExtra("fileIdx", item.file_idx)
        intent.putExtra("fileType", item.file_extension)
        Log.e("sent fileType", "check: " + item.file_extension)
        Log.e("sent fileIdx", "check: " + item.file_idx)
        //intent.putExtra("color",item.popupOptionInfo.file_color)
        //intent.put("item", item.popupOptionInfo)  custom object class를 intent로 넘기는 방법 (parcelable)
        startActivityForResult(intent, FINISH_SETTING_OPTION)
    }

    override fun itemOptionView(item: File, position: Int) {
        Log.e("whatis fileIdx", "here: " + item.file_idx)
        fileStorageViewModel.getPopupOption(item.file_idx)

    }


    override fun itemDelete(item: File, position: Int) {
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        val dialogView = layoutInflater.inflate(R.layout.dialog_item_delete, null)
        val textView: TextView = dialogView.findViewById(R.id.dial_item_delete_tv_message)
        textView.text = item.file_name + "를 삭제하시겠습니까?"
        builder.setView(dialogView)
            .setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
                fileStorageViewModel.deleteItem(item)
                fileStorageViewModel.getPrice(orderIdx)
                Log.e("orderIdx on Delete", "check: " + orderIdx)
            }
            .setNegativeButton("아니오") { dialog: DialogInterface?, which: Int ->

            }
        builder.show()
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
                    Log.e("check orderIdx", "after returning: " + orderIdx)
                    val handler= android.os.Handler()
                    handler.postDelayed(object :Runnable{
                        override fun run() {
                            fileStorageViewModel.getPrice(orderIdx)

                        }
                    },2000)
                }
            }
        }
    }


    private fun getBitmap(file: File): Bitmap? {
        val uri = file.file_uri
        var bitmap: Bitmap? = null
        if (uri != null) {
            bitmap =
                PDFThumbnailUtils.convertPDFtoBitmap(
                    this,
                    uri,
                    0
                )
        }
        return bitmap
    }

    //bitmap을 file로 변환
    private fun bitmapToFile(bitmap:Bitmap): java.io.File? {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = java.io.File(file,"${UUID.randomUUID()}.png")

        try{
            // Compress the bitmap and save in jpg format
            val stream:OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return file
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
                val handler= android.os.Handler()
                handler.postDelayed(object :Runnable{
                    override fun run() {
                        fileStorageViewModel.getPrice(orderIdx)

                    }
                },2000)
            }
        } else if (!flag) {
            for (docUri in docPaths) {
                val filePath = BoosterUtil().getPathFromUri(docUri)
                val fileName = BoosterUtil().getFileName(docUri)
                val fileType = BoosterUtil().getFileType(filePath!!)
                val file = File(-1, fileName, fileType, filePath, docUri)
                val bitmap = getBitmap(file)


                file.thumbnail =  bitmap?.let { bitmapToFile(it) }
                //Log.e("thumbnail File", "Check: " + file.thumbnail + " " + java.io.File(file.file_path))

                fileStorageViewModel.addItem(file)
                fileStorageViewModel.order(orderIdx)
                val handler= android.os.Handler()
                handler.postDelayed(object :Runnable{
                    override fun run() {
                        fileStorageViewModel.getPrice(orderIdx)

                    }
                },2000)
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