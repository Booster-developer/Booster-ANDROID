package com.example.booster.ui.storeDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R
import com.example.booster.ui.store.StoreListFragment

class StoreDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        supportFragmentManager.beginTransaction()
            .replace(R.id.act_store_detail, StoreDetailFragment())
            .commit()
    }
}
