package com.example.booster

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.booster.ui.bottomtap.BottomTabActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, BottomTabActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)

        lottie.run {
            setAnimation("splash_note8.json")
            loop(true)
            playAnimation()
        }
    }
}
