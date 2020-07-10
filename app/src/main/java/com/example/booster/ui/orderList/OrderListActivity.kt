package com.example.booster.ui.orderList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R

class OrderListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        supportFragmentManager.beginTransaction()
            .replace(R.id.act_order_list, OrderListFragment())
            .commit()
    }
}
