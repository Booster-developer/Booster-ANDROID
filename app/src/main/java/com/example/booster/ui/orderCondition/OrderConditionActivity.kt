package com.example.booster.ui.orderCondition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R

class OrderConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_condition)

        supportFragmentManager.beginTransaction()
            .replace(R.id.act_order_condition, OrderConditionFragment())
            .commit()
    }
}
