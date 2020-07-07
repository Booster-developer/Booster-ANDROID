package com.example.booster.ui.storeList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R

class StoreListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_list)

        supportFragmentManager.beginTransaction()
            .replace(R.id.act_store_list, StoreListFragment())
            .commit()
    }
}
