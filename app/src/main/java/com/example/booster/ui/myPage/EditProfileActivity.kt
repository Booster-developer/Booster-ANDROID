package com.example.booster.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.booster.R
import com.example.booster.data.datasource.model.EditProfileData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    var univIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Log.e("onActCreate", "run")
        edit_profile_tv_univ_select_btn.setOnClickListener {
            edit_profile_ll_univ.visibility = View.VISIBLE
        }

        edit_profile_tv_univ_1.setOnClickListener {
            edit_profile_tv_univ_select.text = "숭실대학교"
            univIdx = 1
            edit_profile_ll_univ.visibility = View.GONE
        }
        edit_profile_tv_univ_2.setOnClickListener {
            edit_profile_tv_univ_select.text = "중앙대학교"
            univIdx = 2
            edit_profile_ll_univ.visibility = View.GONE
        }
        edit_profile_tv_univ_3.setOnClickListener {
            edit_profile_tv_univ_select.text = "서울대학교"
            univIdx = 3
            edit_profile_ll_univ.visibility = View.GONE
        }

        edit_profile_button_edit.setOnClickListener {
            edit()
        }
    }

    fun edit() {
        val editJsonData = JSONObject()
        editJsonData.put("user_name", edit_profile_edt_name)
        editJsonData.put("user_university", univIdx)
        editJsonData.put("user_pw", edit_profile_edt_pw_new)

        val body = JsonParser.parseString(editJsonData.toString()) as JsonObject
        BoosterServiceImpl.service.editMyProfile(body = body, token = "")
            .enqueue(object : Callback<EditProfileData> {
                override fun onFailure(call: Call<EditProfileData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(
                    call: Call<EditProfileData>,
                    response: Response<EditProfileData>
                ) {
                    TODO("Not yet implemented")
                }

            })
    }
}