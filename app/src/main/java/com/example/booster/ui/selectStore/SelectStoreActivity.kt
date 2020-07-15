package com.example.booster.ui.selectStore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.Store
import com.example.booster.ui.fileStorage.FileStorageActivity
import kotlinx.android.synthetic.main.activity_select_store.*

class SelectStoreActivity: AppCompatActivity(), StoreListItemClickListener {
    private lateinit var viewModel: SelectStoreViewModel
    private lateinit var mAdapter: StoreListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_store)

        viewModel = ViewModelProvider(this).get(SelectStoreViewModel::class.java)

        mAdapter = StoreListAdapter(this)
        act_select_store_rv_storelist.apply {
            layoutManager = LinearLayoutManager(this@SelectStoreActivity)
            adapter = mAdapter
        }

        viewModel.getStoreList()
        subscribeObservers()
    }
    private fun subscribeObservers(){
        viewModel.storeListLiveData.observe(this, Observer {
            it?.let{
                mAdapter.apply {
                    submitList(it)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == 1000){
                finish()
            }
        }
    }

    override fun onStoreListItemClicked(store: Store, position: Int) {
        val intent = Intent(this, FileStorageActivity::class.java)
        intent.putExtra("storeIdx", store.store_idx)
        intent.putExtra("storeName", store.store_name)
        intent.putExtra("storeAddress", store.store_address)
        startActivityForResult(intent, 1000)
    }
}