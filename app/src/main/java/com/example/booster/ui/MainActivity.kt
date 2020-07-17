package com.example.booster.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.*
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.home.SplashActivity
import com.example.booster.ui.orderList.OrderListActivity
import com.example.booster.ui.payment.PaymentActivity
import com.example.booster.ui.selectStore.SelectStoreActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        act_main_btn_order_condition.onlyOneClickListener {
            val intent = Intent(this@MainActivity, OrderListActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_tap.onlyOneClickListener {
            val intent = Intent(this@MainActivity, BottomTabActivity::class.java)
            startActivity(intent)
        }

        btn_storage.onlyOneClickListener {
            val intent = Intent(this, SelectStoreActivity::class.java)
            startActivity(intent)
        }

        pdf_test.onlyOneClickListener {
            val intent = Intent(this, PdfTestActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_pay.onlyOneClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_login.onlyOneClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        act_main_btn_lottie.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }
    }

}



