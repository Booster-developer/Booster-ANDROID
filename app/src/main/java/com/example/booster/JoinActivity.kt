package com.example.booster

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.booster.data.datasource.model.JoinData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_join.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    var idChk = false

    override fun onCreate(savedInstanceState: Bundle?) {

        var nameChk = false
        idChk = false
        var pwChk = false
        var univIdx = 0
        var checkChk = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        univSelectBtn.setOnClickListener {
            univList.visibility = View.VISIBLE
        }

        univ_1.setOnClickListener {
            univSelected.text = "숭실대학교"
            univIdx = 1
            univList.visibility = View.GONE
        }
        univ_2.setOnClickListener {
            univSelected.text = "중앙대학교"
            univIdx = 2
            univList.visibility = View.GONE
        }
        univ_3.setOnClickListener {
            univSelected.text = "서울대학교"
            univIdx = 3
            univList.visibility = View.GONE
        }

        // 이름입력 focused
        join_name.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                join_name.isSelected = true
                nameChk = true
            } else {
                join_name.isSelected = false
            }
        }
        // 아이디입력 focused
        join_id.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                join_id.isSelected = true
            } else {
                join_id.isSelected = false
            }
        }
        // 비밀번호입력 focused
        join_pw.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                join_pw.isSelected = true
            } else {
                join_pw.isSelected = false
            }
        }
        // 비밀번호확인입력 focused
        join_pw_chk.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                join_pw_chk.isSelected = true
            } else {
                join_pw_chk.isSelected = false

                // 비밀번호 체크
                if (join_pw.text.toString() != join_pw_chk.text.toString()) {
                    join_pw_chk_txt.visibility = View.VISIBLE
                } else {
                    join_pw_chk_txt.visibility = View.INVISIBLE
                    pwChk = true
                }
            }
        }

        // 필수항목 체크
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkChk = true
            } else {
                checkChk = false
            }
        }

        if (nameChk && idChk && pwChk && univIdx == 0 && checkChk) {
            join_btn.setBackgroundResource(R.drawable.bg_btn_gradation)
        } else {
            join_btn.setBackgroundResource(R.drawable.join_btn_2)
        }

        // 아이디 중복 확인
//        val checkIdJsonData = JSONObject()
//        checkIdJsonData.put("user_id", join_id.text.toString())
//
//        val body = JsonParser.parseString(checkIdJsonData.toString()) as JsonObject
        id_chk_btn.setOnClickListener {
            checkId()

//            BoosterServiceImpl.service.requestCheckId(body)
//                .enqueue(object : Callback<JoinData> {
//                    override fun onFailure(call: Call<JoinData>, t: Throwable) {
//                        Log.e("errrrrrrrrrr", t.toString())
//                    }
//
//                    override fun onResponse(
//                        call: Call<JoinData>,
//                        response: Response<JoinData>
//                    ) {
//                        Log.e("aaaaaaaaaaaa", join_id.text.toString())
//                        Log.e("llllllllll", response.body().toString())
//                        if (response.body()!!.success) {
//                            id_chk_fail.visibility = View.INVISIBLE
//                            id_chk_success.visibility = View.VISIBLE
//                            idChk = true
//                        } else {
//                            id_chk_success.visibility = View.INVISIBLE
//                            id_chk_fail.visibility = View.VISIBLE
//                        }
//                    }
//                })
        }

        // 회원가입 버튼 활성화
        if (nameChk && idChk && pwChk && univIdx == 0 && checkChk) {
            join_btn.isClickable = false
            join_btn.setBackgroundResource(R.drawable.bg_btn_gradation)
        } else {
            join_btn.setBackgroundResource(R.drawable.join_btn_2)
        }

        join_btn.setOnClickListener {

            if (!checkBox.isChecked) {
                Toast.makeText(this, "필수 항목을 체크해주세요", Toast.LENGTH_SHORT).show()
            }

            // 회원가입 request
            val joinJsonData = JSONObject()
            joinJsonData.put("user_id", join_id)
            joinJsonData.put("user_name", join_name)
            joinJsonData.put("user_pw", join_pw)
            joinJsonData.put("user_university", univIdx)

            val body = JsonParser.parseString(joinJsonData.toString()) as JsonObject

            if (!(nameChk && idChk && pwChk && univIdx == 0 && checkChk)) {
                Toast.makeText(this, "모든 항목을 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                BoosterServiceImpl.service.requestJoin(body)
                    .enqueue(object : Callback<JoinData> {
                        override fun onFailure(call: Call<JoinData>, t: Throwable) {
                            Toast.makeText(this@JoinActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }

                        // 아이디와 비밀번호 로그인 화면으로 전달
                        override fun onResponse(
                            call: Call<JoinData>,
                            response: Response<JoinData>
                        ) {
                            if (response.isSuccessful) {
                                val message = response.body()!!.message
                                Toast.makeText(this@JoinActivity, message, Toast.LENGTH_SHORT)
                                    .show()
                                if (response.body()!!.success) {
                                    val intent = Intent()
                                    intent.putExtra("id", join_id.text.toString())
                                    intent.putExtra("password", join_pw.text.toString())
                                    setResult(Activity.RESULT_OK, intent)
                                    finish()
                                }
                            }
                        }
                    })
            }
        }
    }

    fun checkId() {
        // 아이디 중복 확인
        val checkIdJsonData = JSONObject()
        checkIdJsonData.put("user_id", join_id.text.toString())
        val body = JsonParser.parseString(checkIdJsonData.toString()) as JsonObject

        BoosterServiceImpl.service.requestCheckId(body).enqueue(object : Callback<JoinData> {
                override fun onFailure(call: Call<JoinData>, t: Throwable) {
                    Log.e("errrrrrrrrrr", t.toString())
                }

                override fun onResponse(
                    call: Call<JoinData>,
                    response: Response<JoinData>
                ) {
                    Log.e("aaaaaaaaaaaa", join_id.text.toString())
                    Log.e("llllllllll", response.body().toString())
                    if (response.body()!!.success) {
                        id_chk_fail.visibility = View.INVISIBLE
                        id_chk_success.visibility = View.VISIBLE
                        idChk = true
                    } else {
                        id_chk_success.visibility = View.INVISIBLE
                        id_chk_fail.visibility = View.VISIBLE
                    }
                }
            })

    }
}
