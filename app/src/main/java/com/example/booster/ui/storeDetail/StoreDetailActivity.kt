package com.example.booster.ui.storeDetail

import android.os.Bundle
import android.util.Log
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
    var idx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        viewModel = ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.vm = (this@StoreDetailActivity).viewModel

        idx = intent.getIntExtra("position", 0)
        viewModel.getStoreDetail(idx)

        viewModel.favStatus.observe(this, Observer {
            Log.e("result -> ", it.message)
            if(it.status==200){
                act_store_detail_iv_star.setBackgroundResource(R.drawable.store_detail_ic_star_inactive)
            } else if(it.status==201){
                act_store_detail_iv_star.setBackgroundResource(R.drawable.store_detail_ic_star_active)
            }
        })

        act_store_detail_iv_star.setOnClickListener {
            viewModel.putStoreFav(4)
        }
    }

}
