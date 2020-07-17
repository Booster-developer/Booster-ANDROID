package com.example.booster.ui.storeDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.example.booster.databinding.ActivityStoreDetailBinding
import com.example.booster.listener.onlyOneClickListener
import com.example.booster.ui.fileStorage.FileStorageActivity
import com.example.booster.util.UserManager
import kotlinx.android.synthetic.main.activity_store_detail.*

class StoreDetailActivity : AppCompatActivity() {

    var markers = arrayListOf<MarkerData>()

    lateinit var viewModel: StoreDetailViewModel
    lateinit var binding: ActivityStoreDetailBinding
    var idx : Int = 0
    var store = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("User token", UserManager.token)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        viewModel = ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.vm = (this@StoreDetailActivity).viewModel


        idx = intent.getIntExtra("storeIdx", 0)
        viewModel.getStoreDetail(idx)

        viewModel.favStatus.observe(this, Observer {
            Log.e("result -> ", it.message)
            if(it.status==200){
                act_store_detail_iv_star.setImageResource(R.drawable.store_detail_ic_star_inactive)
            } else if(it.status==201){
                act_store_detail_iv_star.setImageResource(R.drawable.store_detail_ic_star_active)
            }
        })

        act_store_detail_iv_star.onlyOneClickListener {
            viewModel.putStoreFav(idx)
        }

        act_store_detail_map.onlyOneClickListener {
            markers.clear()
            val intent = Intent(this, StoreDetailMapActivity::class.java)
            markers.add(
                MarkerData(
                    latitude = viewModel.storeDetail.value?.data?.store_x_location,
                    longitude = viewModel.storeDetail.value?.data?.store_y_location,
                    name = viewModel.storeDetail.value?.data?.store_name,
                    idx = 0
                )
            )
            intent.putParcelableArrayListExtra("marker", markers)
            startActivity(intent)
        }

        act_store_detail_btn_order.onlyOneClickListener {
            viewModel.getOrderIdx(idx)
            viewModel.orderIdx.observe(
                this, Observer {
                    Log.e("it", it.toString())
                }
            )
            val orderIdx = viewModel.orderIdxMutableLiveData
            Log.e("orderIdx", orderIdx.toString())
            val intent = Intent(this, FileStorageActivity::class.java)
            intent.putExtra("storeIdx", idx)
            intent.putExtra("storeName", viewModel.storeDetail.value?.data?.store_name)
            intent.putExtra("storeAddress", viewModel.storeDetail.value?.data?.store_address)
            startActivity(intent)
        }

        act_store_detail_iv_back.onlyOneClickListener {
            finish()
        }
    }
}
