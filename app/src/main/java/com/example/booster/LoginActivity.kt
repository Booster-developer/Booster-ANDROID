package com.example.booster

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        goto_join.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivityForResult(intent, 100)
        }

    }
}