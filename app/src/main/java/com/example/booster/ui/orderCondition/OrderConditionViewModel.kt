package com.example.booster.ui.orderCondition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.OrderConditionData

class OrderConditionViewModel : ViewModel() {

    private var _orderList = MutableLiveData<ArrayList<OrderConditionData>>() //변경 가능한 mutableLiveData 변수
    val orderList : LiveData<ArrayList<OrderConditionData>> get() = _orderList //LiveData 변수인 newsList는 변경이 안되므로 변경 가능한 _newsList를 가져옴

    val dummy = arrayListOf(
        OrderConditionData(
            name = "가게 1",
            status = 1
        ),
        OrderConditionData(
            name = "가게 2",
            status = 2
        ),
        OrderConditionData(
            name = "가게 3",
            status = 3
        ),
        OrderConditionData(
            name = "가게 4",
            status = 1
        ),
        OrderConditionData(
            name = "가게 5",
            status = 2
        ),
        OrderConditionData(
            name = "가게 6",
            status = 3
        ),
        OrderConditionData(
            name = "가게 4",
            status = 1
        ),
        OrderConditionData(
            name = "가게 5",
            status = 2
        ),
        OrderConditionData(
            name = "가게 6",
            status = 3
        )
    )

    init {
        _orderList.postValue(dummy)
    }
}
