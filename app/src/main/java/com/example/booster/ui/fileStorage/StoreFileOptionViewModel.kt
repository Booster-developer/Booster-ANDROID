package com.example.booster.ui.fileStorage

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieCompositionFactory.fromJson
import com.example.booster.data.datasource.model.DefaultData
import com.example.booster.data.datasource.model.PopupOptionInfo
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFileOptionViewModel : ViewModel() {
    val requestToServer = BoosterServiceImpl

    private val _optionMutableLiveData: MutableLiveData<PopupOptionInfo> = MutableLiveData()
    val optionLiveData: LiveData<PopupOptionInfo>
        get() = _optionMutableLiveData

    fun setOptionData(fileIdx: Int, body: JsonObject) {

        //val optionData = Gson().fromJson(body.toString(), PopupOptionInfo:: class.java)
        Log.e("body", "check: " + body.toString())
        requestToServer.service.changeOption(
            fileIdx, body
        ).enqueue(object : Callback<DefaultData> {
            override fun onFailure(call: Call<DefaultData>, t: Throwable) {
                //통신 실패
                Log.e("onResponse", "통신 실패")
            }

            override fun onResponse(
                call: Call<DefaultData>,
                response: Response<DefaultData>
            ) {
                if (response.isSuccessful){
                    //통신 성공
                    Log.e("onResponse", response.message() + "통신 성공")
                    //Log.e("optionData: ", "check" + optionData)
                }
            }
        })
    }

    fun getPopupOption(fileIdx: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = BoosterServiceImpl.serviceFileUpload.getPopupOption(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8",
                fileIdx
            )
            if (response.status == 200) {
                val data = response.data
                _optionMutableLiveData.postValue(data)

                Log.e("getPopupOption", "check: " + response.data)

            }
        }
    }
}