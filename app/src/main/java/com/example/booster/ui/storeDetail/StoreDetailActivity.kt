package com.example.booster.ui.storeDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.databinding.ActivityStoreDetailBinding

class StoreDetailActivity : AppCompatActivity() {
    lateinit var storeDetailViewModel: StoreDetailViewModel

    lateinit var storeDetailBinding: ActivityStoreDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        storeDetailViewModel =
            ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)

        storeDetailBinding.vm = storeDetailViewModel

        val position = intent.getIntExtra("position", -1)

        Toast.makeText(this@StoreDetailActivity, "$position", Toast.LENGTH_SHORT).show()

//        getObserved()
    }

    fun getObserved() {
//        storeDetailViewModel.viewModel.observe(this, Observer {
//            storeDetailBinding.vm =it
//        })
//        storeDetailViewModel.subway.observe(this, Observer {
//            storeDetailBinding.subway = it
//        })
    }
}
