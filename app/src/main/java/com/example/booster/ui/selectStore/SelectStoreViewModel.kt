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
import com.example.booster.util.UserManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SelectStoreViewModel : ViewModel() {
    private val _storeListMutableLiveData: MutableLiveData<List<Store>> = MutableLiveData()
    val storeListLiveData
        get() = _storeListMutableLiveData

    fun setListItems(list: List<Store>) {
        _storeListMutableLiveData.value = list
    }

    fun getStoreList() {
        viewModelScope.launch(IO) {
            val response = BoosterServiceImpl.serviceFileUpload.getStoreListByJeongRok(
                UserManager.token.toString()
            )
            if (response.status == 200) {
                val data = response.data
                Log.e("storeIdx", "check: " + data?.recent_order_store?.store_idx)
                Log.e("storeIdx", "check: " + data?.favorite_store)
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
            } else {
                Log.e("get selectstorelist error", "message")
            }
        }
    }
}