package com.example.booster.ui.storeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.StoreListData

class StoreListViewModel : ViewModel() {

    private var _storeList = MutableLiveData<ArrayList<StoreListData>>() //변경 가능한 mutableLiveData 변수
    val storeList : LiveData<ArrayList<StoreListData>> get() = _storeList //LiveData 변수인 newsList는 변경이 안되므로 변경 가능한 _newsList를 가져옴

    val dummy = arrayListOf(
        StoreListData(
            name = "가게 1",
            store_favorite = 1
        ),
        StoreListData(
            name = "가게 2",
            store_favorite = 0
        ),
        StoreListData(
            name = "가게 3",
            store_favorite = 1

        ),
        StoreListData(
            name = "가게 3",
            store_favorite = 1

        ),
        StoreListData(
            name = "가게 3",
            store_favorite = 1

        ),
        StoreListData(
            name = "가게 3",
            store_favorite = 0

        ),
        StoreListData(
            name = "가게 3",
            store_favorite = 1

        )
    )

    fun reload(){
        _storeList.postValue(dummy)
    }

    init {
        _storeList.postValue(dummy)
    }
}
