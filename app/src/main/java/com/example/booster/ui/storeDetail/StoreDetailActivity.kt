package com.example.booster.ui.storeDetail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.databinding.ActivityStoreDetailBinding
import kotlinx.android.synthetic.main.activity_store_detail.*

class StoreDetailActivity : AppCompatActivity() {

    lateinit var viewModel: StoreDetailViewModel
    lateinit var binding: ActivityStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        viewModel = ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)

        binding.vm = (this@StoreDetailActivity).viewModel
        viewModel.getStoreDetail(4)

        viewModel.storeDetail.observe(this, Observer {
            binding.storeDetailRes = it
        })
        viewModel._favStatus.observe(this, Observer {
            Log.e("result -> ", it.message)
            if(it.status==200){
                act_store_detail_iv_star.setBackgroundResource(R.drawable.store_detail_ic_star_active)
            }
        })

        act_store_detail_iv_star.setOnClickListener {
            viewModel.postStoreFav(4)
        }
    }

}
