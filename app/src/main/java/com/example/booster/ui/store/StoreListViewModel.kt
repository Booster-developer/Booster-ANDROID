package com.example.booster.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.StoreListData

class StoreListViewModel : ViewModel() {

    private var _storeList = MutableLiveData<ArrayList<StoreListData>>() //변경 가능한 mutableLiveData 변수
    val storeList : LiveData<ArrayList<StoreListData>> get() = _storeList //LiveData 변수인 newsList는 변경이 안되므로 변경 가능한 _newsList를 가져옴

    val dummy = arrayListOf(
        StoreListData(
            name = "가게 1"
        ),
        StoreListData(
            name = "가게 2"
        ),
        StoreListData(
            name = "가게 3"
        ),
        StoreListData(
            name = "가게 4"
        ),
        StoreListData(
            name = "가게 5"
        ),
        StoreListData(
            name = "가게 6"
        ),
        StoreListData(
            name = "가게 7"
        ),
        StoreListData(
            name = "가게 8"
        )
    )

    init {
        _storeList.postValue(dummy)
    }
}
