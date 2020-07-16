package com.example.booster.ui.selectStore

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booster.data.datasource.model.Store
import com.example.booster.data.datasource.model.StoreList
import com.example.booster.data.remote.network.BoosterService
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.ui.selectStore.StoreListAdapter.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SelectStoreViewModel : ViewModel() {
    private val _storeListMutableLiveData: MutableLiveData<List<Store>> = MutableLiveData()
    val storeListLiveData
        get() = _storeListMutableLiveData

    fun setListItems(list: List<Store>) {
        _storeListMutableLiveData.value = list
    }

    fun getOrder() {
        _storeListMutableLiveData
    }

    fun getStoreList() {
        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.getStoreListByJeongRok(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkeCI6MSwiaWF0IjoxNTk0MDI1NzE2LCJleHAiOjE1OTc2MjU3MTYsImlzcyI6IkJvb3N0ZXIifQ.FtWfnt4rlyYH9ZV3TyOjLZXOkeR7ya96afmA0zJqTI8"
            )
            if (response.status == 200) {
                val data = response.data
                Log.e("storeIdx", "check: " + data?.recent_order_store?.store_idx)
                val list = mutableListOf<Store>()
                data?.let {
                    it.recent_order_store?.let { store ->
                        list.add(
                            Store(
                                store.store_idx,
                                store.store_name,
                                store.store_image,
                                store.store_address,
                                ViewType.LATEST.ordinal
                            )
                        )
                    }
                    it.favorite_store?.let { tempList ->
                        tempList.forEach { store ->
                            list.add(
                                Store(
                                    store.store_idx,
                                    store.store_name,
                                    store.store_image,
                                    store.store_address,
                                    ViewType.FAVORITE.ordinal
                                )
                            )
                        }
                    } ?: list.add(Store(-1, "", "", "", ViewType.FAVORITE.ordinal))
                    it.store_all.let { tempList ->
                        tempList.forEach { store ->
                            list.add(
                                Store(
                                    store.store_idx,
                                    store.store_name,
                                    store.store_image,
                                    store.store_address,
                                    ViewType.EXTRA.ordinal
                                )
                            )
                        }
                    }
                }
                _storeListMutableLiveData.postValue(list)
            }
        }
    }
}