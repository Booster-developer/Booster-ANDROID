package com.example.booster.ui.storeDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.booster.R

class StoreDetailActivity : AppCompatActivity() {

    lateinit var viewModel: StoreDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)



        val position = intent.getIntExtra("position", -1)

        Toast.makeText(this@StoreDetailActivity, "$position", Toast.LENGTH_SHORT).show()
    }
}
