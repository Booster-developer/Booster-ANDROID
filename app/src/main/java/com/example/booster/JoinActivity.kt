package com.example.booster

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    var nameChk = false
    var idChk = false
    var pwChk = false
    var univIdx = 0
    var checkChk = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        join_tv_univ_select_btn.onlyOneClickListener {
            join_ll_univ.visibility = View.VISIBLE
        }

        join_tv_univ_1.onlyOneClickListener {
            join_tv_univ_select.text = "숭실대학교"
            univIdx = 1
            join_ll_univ.visibility = View.GONE
        }
        join_tv_univ_2.onlyOneClickListener {
            join_tv_univ_select.text = "중앙대학교"
            univIdx = 2
            join_ll_univ.visibility = View.GONE
        }
        join_tv_univ_3.onlyOneClickListener {
            join_tv_univ_select.text = "서울대학교"
            univIdx = 3
            join_ll_univ.visibility = View.GONE
        }

        // 이름입력 focused
        join_edt_name.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                join_edt_name.isSelected = true
                nameChk = true
            } else {
                join_edt_name.isSelected = false
            }
            checkJoin()
        }
        // 아이디입력 focused
        join_edt_id.setOnFocusChangeListener { v, hasFocus ->
            join_edt_id.isSelected = hasFocus
            checkJoin()
        }
        // 비밀번호입력 focused
        join_edt_pw.setOnFocusChangeListener { v, hasFocus ->
            join_edt_pw.isSelected = hasFocus
            checkJoin()
        }
        // 비밀번호확인입력 focused
        join_edt_pw_chk.setOnFocusChangeListener { v, hasFocus ->
            join_edt_pw_chk.isSelected = hasFocus
            // 비밀번호 체크
            if (join_edt_pw.text.toString() != join_edt_pw_chk.text.toString()) {
                join_tv_pw_check_fail.visibility = View.VISIBLE
            } else {
                join_tv_pw_check_success.visibility = View.INVISIBLE
                pwChk = true
            }
            checkJoin()
        }
        join_edt_pw_chk.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(join_edt_pw_chk.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
        // 필수항목 체크
        join_checkbox_agree_1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkChk = true
            } else {
                checkChk = false
            }
            checkJoin()
        }

        // 아이디 중복 확인
        join_tv_id_chk.onlyOneClickListener {
            checkId()
        }

        // 회원가입 request
        join_btn_join.onlyOneClickListener {
            join()
        }
    }

    // 아이디 중복 확인
    fun checkId() {
        val checkIdJsonData = JSONObject()
        checkIdJsonData.put("user_id", join_edt_id.text.toString())
        val body = JsonParser.parseString(checkIdJsonData.toString()) as JsonObject

        BoosterServiceImpl.service.requestCheckId(body).enqueue(object : Callback<JoinData> {
            override fun onFailure(call: Call<JoinData>, t: Throwable) {
                Log.e("error", t.toString())
            }

            override fun onResponse(
                call: Call<JoinData>,
                response: Response<JoinData>
            ) {
                if (response.body()!!.success) {
                    join_tv_id_check_fail.visibility = View.INVISIBLE
                    join_tv_id_check_success.visibility = View.VISIBLE
                    idChk = true
                } else {
                    join_tv_id_check_success.visibility = View.INVISIBLE
                    join_tv_id_check_fail.visibility = View.VISIBLE
                }
            }
        })
    }

    fun join() {
        val joinJsonData = JSONObject()
        joinJsonData.put("user_id", join_edt_id)
        joinJsonData.put("user_name", join_edt_name)
        joinJsonData.put("user_pw", join_edt_pw)
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
                                intent.putExtra("id", join_edt_id.text.toString())
                                intent.putExtra("password", join_edt_pw.text.toString())
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }
                    }
                })
        }
    }

    // 회원가입 버튼 활성화
    fun checkJoin() {
        if (nameChk && idChk && pwChk && univIdx != 0 && checkChk) {
            join_btn_join.setBackgroundResource(R.drawable.bg_btn_gradation)
            join_btn_join.setTextColor(getColor(R.color.white))
        } else {
            join_btn_join.setBackgroundResource(R.drawable.join_btn_2)
        }
    }
}
