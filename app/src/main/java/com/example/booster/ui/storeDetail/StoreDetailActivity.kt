package com.example.booster.ui.storeDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R
import com.example.booster.databinding.ActivityStoreDetailBinding

class StoreDetailActivity : AppCompatActivity() {

    lateinit var viewModel: StoreDetailViewModel
    lateinit var binding: ActivityStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        viewModel = ViewModelProvider(this@StoreDetailActivity).get(StoreDetailViewModel::class.java)


        binding.vm = viewModel

        viewModel.getData()

        val position = intent.getIntExtra("position", -1)

        Toast.makeText(this@StoreDetailActivity, "$position", Toast.LENGTH_SHORT).show()

    }

}
