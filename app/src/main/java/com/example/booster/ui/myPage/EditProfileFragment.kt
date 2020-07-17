package com.example.booster.ui.myPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.booster.R
import com.example.booster.data.datasource.model.EditProfileData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.univList
import kotlinx.android.synthetic.main.fragment_edit_profile.univSelectBtn
import kotlinx.android.synthetic.main.fragment_edit_profile.univ_1
import kotlinx.android.synthetic.main.fragment_edit_profile.univ_2
import kotlinx.android.synthetic.main.fragment_edit_profile.univ_3
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileFragment : Fragment() {
    var univIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        univSelectBtn.setOnClickListener {
            univList.visibility = View.VISIBLE
        }

        univ_1.setOnClickListener {
            edit_profile_tv_univ_select.text = "숭실대학교"
            univIdx = 1
            univList.visibility = View.GONE
        }
        univ_2.setOnClickListener {
            edit_profile_tv_univ_select.text = "중앙대학교"
            univIdx = 2
            univList.visibility = View.GONE
        }
        univ_3.setOnClickListener {
            edit_profile_tv_univ_select.text = "서울대학교"
            univIdx = 3
            univList.visibility = View.GONE
        }

        edit_btn.setOnClickListener {
            edit()
        }

        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    fun edit() {
        val editJsonData = JSONObject()
        editJsonData.put("user_name", edit_profile_edt_name)
        editJsonData.put("user_university", univIdx)
        editJsonData.put("user_pw", edit_profile_edt_pw_new)

        val body = JsonParser.parseString(editJsonData.toString()) as JsonObject
//        BoosterServiceImpl.service.editMyProfile(body = body, token = "")
//            .enqueue(object : Callback<EditProfileData> {
//                override fun onFailure(call: Call<EditProfileData>, t: Throwable) {
//                    Log.e("error", t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<EditProfileData>,
//                    response: Response<EditProfileData>
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//            })
    }
}
