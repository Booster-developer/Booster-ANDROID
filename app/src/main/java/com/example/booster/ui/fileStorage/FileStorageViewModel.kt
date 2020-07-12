package com.example.booster.ui.fileStorage

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booster.data.datasource.model.File
import com.example.booster.data.datasource.model.PopupOptionInfo
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.util.BoosterUtil
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class FileStorageViewModel : ViewModel() {
    private val _fileMutableLiveData: MutableLiveData<ArrayList<File>> = MutableLiveData()
    val fileLiveData: LiveData<ArrayList<File>>
        get() = _fileMutableLiveData

    //private lateinit var arrList: ArrayList<File>

//    private val _statusLiveData: MutableLiveData<Int> = MutableLiveData()
//    val statusLiveData: LiveData<Int>
//        get() = _statusLiveData

    init {
        _fileMutableLiveData.value = ArrayList()
    }

    fun getFileList() {
//        BoosterServiceImpl.service.getFileList(1).enqueue(object : Callback<FileResponse> {
//            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
//                Log.e("error : ", t.message)
//            }
//
//            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
//                val fileResponse = response.body()
//                Log.e("data", fileResponse.toString())
//                val list = ArrayList<File>()
//                list.add(fileResponse!!.data.file_info[0])
//                list.add(fileResponse.data.file_info[1])
//                _fileMutableLiveData.value = list
//            }
//        })
    }

    fun deleteItem(item: File) {
        val list = _fileMutableLiveData.value
        list?.remove(item)
        _fileMutableLiveData.value = list
    }

    fun addItem(item: File) {
        val list = _fileMutableLiveData.value
        list?.add(item)
        _fileMutableLiveData.value = list
    }

    fun order() {
        val idx = 6
        val file = _fileMutableLiveData.value?.get(0)
        val filePath = BoosterUtil().getPathFromUri(file?.file_uri)
/*        val requestFile: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            uri
        )*/

        val requestBody = RequestBody.create(
            MediaType.parse("image/*"), java.io.File(filePath)
        )
        val multipartBody =
            MultipartBody.Part.createFormData("file", file!!.file_name, requestBody)

        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.service.postUploadFile(
                idx,
                multipartBody
            )
            if (response.status == 200) {
                val data = response.data
                Log.e("check", "fileIdx : ${data?.file_idx}")
            }
            // _statusLiveData.postValue(response.status)
        }
    }

    fun setOptions(
        popupOptionInfo: PopupOptionInfo
    ){
        val file = _fileMutableLiveData.value?.get(0)
        file?.popupOptionInfo = popupOptionInfo

        //_fileMutableLiveData.value =

    }


}