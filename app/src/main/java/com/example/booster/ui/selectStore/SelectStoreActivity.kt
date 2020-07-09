package com.example.booster.ui.selectStore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booster.R
import com.example.booster.ui.fileStorage.FileStorageActivity
import kotlinx.android.synthetic.main.activity_select_store.*

class SelectStoreActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_store)
        button.setOnClickListener {
            val intent = Intent(this, FileStorageActivity::class.java)
            startActivityForResult(intent, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == 1000){
                finish()
            }
        }
    }
}