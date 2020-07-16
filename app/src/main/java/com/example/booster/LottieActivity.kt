package com.example.booster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lottie.*

class LottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        lottie.run {
            setAnimation("splash_note8.json")
            loop(true)
            playAnimation()
        }
    }
}
