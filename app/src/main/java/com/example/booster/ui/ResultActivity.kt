package com.example.booster.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booster.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        textView2.setText(intent.getStringExtra("color"))
        textView4.setText(intent.getStringExtra("direction"))
        textView6.setText(intent.getStringExtra("side"))
        textView8.setText(intent.getStringExtra("combine")+"개")

        if(intent.hasExtra("range")){
            textView10.setText(intent.getStringExtra("range"))
        }
        if(intent.hasExtra("rangeMin")){
            textView10.text = intent.getStringExtra("rangeMin") +" ~ " + intent.getStringExtra("rangeMax") + "페이지"
        }

        textView12.setText(intent.getStringExtra("num")+"부")

    }
}
