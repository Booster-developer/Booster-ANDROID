package com.example.booster.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R
import com.example.booster.ui.orderCondition.OrderConditionActivity
import com.example.booster.ui.store.StoreListActivity
import com.example.booster.ui.store.StoreListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        act_main_btn_store.setOnClickListener {
            val intent = Intent(this@MainActivity, StoreListActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_order_condition.setOnClickListener {
            val intent = Intent(this@MainActivity, OrderConditionActivity::class.java)
            startActivity(intent)
        }
    }
}
