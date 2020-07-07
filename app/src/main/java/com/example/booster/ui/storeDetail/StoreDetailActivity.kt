package com.example.booster.ui.storeDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.databinding.ActivityStoreDetailBinding

class StoreDetailActivity : AppCompatActivity() {
    private lateinit var storeDetailViewModel: StoreDetailViewModel
    lateinit var storeDetailBinding: ActivityStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)

        storeDetailViewModel =
            ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)

        storeDetailBinding.vm = storeDetailViewModel

        storeDetailViewModel.getData()

        val position = intent.getIntExtra("position", -1)

        Toast.makeText(this@StoreDetailActivity, "$position", Toast.LENGTH_SHORT).show()

    }

}
