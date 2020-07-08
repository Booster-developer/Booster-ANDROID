package com.example.booster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        univSelectBtn.setOnClickListener {
            Log.d("eee",univSelected.text.toString())
            if (univSelected.text == ""){
                Log.d("eee","bbbbb")
                univList.visibility = View.VISIBLE
            }
        }

        univ_1.setOnClickListener {
            univSelected.text = "숭실대학교"
        }
        univ_2.setOnClickListener {
            univSelected.text = "중앙대학교"
        }
        univ_3.setOnClickListener {
            univSelected.text = "서울대학교"
        }
    }
}
