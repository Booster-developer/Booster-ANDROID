package com.example.booster.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R
import com.example.booster.ui.orderList.OrderListFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction()
            .replace(R.id.act_home, HomeFragment())
            .commit()

    }
}
