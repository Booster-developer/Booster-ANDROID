package com.example.booster.ui.orderList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.DefaultData
import com.example.booster.data.datasource.model.Order
import com.example.booster.data.datasource.model.OrderList
import com.example.booster.data.datasource.model.OrderListData
import com.example.booster.data.repository.OrderListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class OrderListViewModel : ViewModel() {

    private val orderListRepository = OrderListRepository()
    private val disposables = CompositeDisposable()

    private var _orderList = MutableLiveData<ArrayList<OrderList>>()
    val orderList : LiveData<ArrayList<OrderList>> get() = _orderList

    private var _pickUpRes = MutableLiveData<DefaultData>()
    val pickUpRes : LiveData<DefaultData> get() = _pickUpRes

    var orderInfo = MutableLiveData<Order>()

    fun getOrderList(){
        disposables.add(orderListRepository.getOrderList()
            .observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {}
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {}
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                // onResponse
//                Log.e("getOrderList 응답 성공 : ", it.toString())
                orderInfo.postValue(it.data)
                _orderList.postValue(it.data.order_list)
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }

    fun putPickUp(orderIdx : Int){
        disposables.add(orderListRepository.putPickUp(orderIdx)
            .observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {}
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {}
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                // onResponse
                Log.e("putPickUp 응답 성공 : ", it.toString())
                _pickUpRes.postValue(it)
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }
}
