package com.example.booster.ui.fileStorage

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booster.data.datasource.model.File
import com.example.booster.data.datasource.model.PopupOptionInfo
import com.example.booster.data.datasource.model.Wait
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.util.BoosterUtil
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileOutputStream
import java.io.IOException


class FileStorageViewModel : ViewModel() {
    private val _fileMutableLiveData: MutableLiveData<ArrayList<File>> = MutableLiveData()
    val fileLiveData: LiveData<ArrayList<File>> get() = _fileMutableLiveData

    private val _popupOptionMutableLiveData: MutableLiveData<PopupOptionInfo> = MutableLiveData()
    val popupOptionLiveData: LiveData<PopupOptionInfo> get() = _popupOptionMutableLiveData

    private val _waitlistMutableLiveData: MutableLiveData<Wait> = MutableLiveData()
    val waitlistLiveData: LiveData<Wait> get() = _waitlistMutableLiveData

    private val _responseMessageMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val responseMessageLiveData: LiveData<String> get() = _responseMessageMutableLiveData

    private val _orderIdxMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    val orderIdxMutableLiveData: LiveData<Int> get() = _orderIdxMutableLiveData


    //private lateinit var arrList: ArrayList<File>

//    private val _statusLiveData: MutableLiveData<Int> = MutableLiveData()
//    val statusLiveData: LiveData<Int>
//        get() = _statusLiveData

    init {
        _fileMutableLiveData.value = ArrayList()
    }


    fun getOrderIdx(storeIdx: Int){
        viewModelScope.launch(IO){
            val response = BoosterServiceImpl.serviceFileUpload.getOrderIdx(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                storeIdx
            )
            if (response.status == 200) {
                val data = response.data
                Log.e("orderIdx", "check: " + data?.order_idx)
                _orderIdxMutableLiveData.postValue(data?.order_idx)
            }
        }
    }
    fun getPrice(orderIdx: Int){
        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.getFileList(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                orderIdx
            )
            if (response.status == 200) {
                val data = response.data
                Log.e("orderPrice", "check: " + data?.order_price)
                _waitlistMutableLiveData.postValue(data)
            }
        }
    }

    fun getPopupOption(fileIdx: Int) {
        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.getPopupOption(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                fileIdx
            )
            if (response.status == 200) {
                val data = response.data
                _popupOptionMutableLiveData.postValue(data)
            }
        }
    }


    fun deleteItem(item: File) {
        val list = _fileMutableLiveData.value
        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.deleteFile(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                item.file_idx
            )
            if (response.status == 200) {
                Log.e("File Successfully deleted with file Idx: ", item.file_idx.toString())
            }
        }
        list?.remove(item)
        _fileMutableLiveData.value = list
    }

    fun addItem(item: File) {
        val list = _fileMutableLiveData.value
        list?.add(item)
        _fileMutableLiveData.value = list
    }


    fun order(orderIdx: Int) {
        //val file = _fileMutableLiveData.value?.get(0)
        val file = _fileMutableLiveData.value?.get((_fileMutableLiveData.value?.size!! - 1))
        val imageFile = java.io.File(file?.file_path)
        val docFile = java.io.File(file?.file_path)
        val thumbnailFile = file?.thumbnail

        val thumbnailFilename = thumbnailFile.toString().split("/")

        val name = thumbnailFilename?.get(thumbnailFilename.size - 1)

        Log.e("thumbnail uri", "check: " + Uri.parse(thumbnailFile.toString()) + " " + name)

        Log.e("asdf", "check : " + file?.file_path + " " + file?.file_name)
/*        val requestFile: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            uri
        )*/

        var requestBody: RequestBody? = null
        var requestBody2: RequestBody? = null

        when (file?.file_extension) {
            ".png" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("image/png"), imageFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/png"), imageFile
                )
            }
            ".pdf" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("application/pdf"), docFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/png"), thumbnailFile
                )
            }
            ".docx" -> requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), docFile
            )
            ".jpeg", ".jpg" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("image/jpeg"), imageFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/jpeg"), imageFile
                )
            }
        }
        Log.e(
            "pdfcheck",
            "check: " + requestBody + " " + file?.file_extension + " " + file?.file_name
        )
        val multipartBody =
            MultipartBody.Part.createFormData("file", file?.file_name, requestBody)

        val multipartBody2 =
            MultipartBody.Part.createFormData("thumbnail", "png", requestBody2)

        //Log.e("thumbnail", "check: " + thumbnailFile + " " + name)
        Log.e("pdf file", "check: " + docFile + " " + file?.file_name)
        Log.e("multipartBody", "check: " + multipartBody + " " + multipartBody2)


        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.postUploadFile(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                orderIdx,
                multipartBody,
                multipartBody2
            )
            if (response.status == 200) {
                val data = response.data
                val fileIdx = data?.file_idx
                val latestFile = _fileMutableLiveData.value?.get((_fileMutableLiveData.value?.size!! - 1))
                if(latestFile?.file_idx == -1){
                    fileIdx?.let{
                        latestFile.file_idx = it
                    }
                }
                Log.e("check", "fileIdx : ${data?.file_idx} " + _fileMutableLiveData.value?.get((_fileMutableLiveData.value?.size!! - 1))?.file_idx)
            } else {
//                withContext(Main){
//
//                }
                _responseMessageMutableLiveData.postValue(response.message)
            }

            //  _statusLiveData.postValue(response.status)
        }
    }


}