package com.example.booster.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booster.data.datasource.model.HomeData
import com.example.booster.data.repository.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository()
    private val disposables = CompositeDisposable()

    var homeRes = MutableLiveData<HomeData>()

    fun getHome(){
        disposables.add(homeRepository.getHome()
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
                Log.e("getHome 응답 성공 : ", it.toString())
                homeRes.postValue(it)
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }
}
