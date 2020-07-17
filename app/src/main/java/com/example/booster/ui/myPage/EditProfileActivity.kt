package com.example.booster.ui.myPage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.booster.R
import com.example.booster.data.datasource.model.EditProfileData
import com.example.booster.data.datasource.model.NoticeData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    var univIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // 뒤로가기 버튼
        edit_profile_img_back.setOnClickListener {
            finish()
        }

        // 마이페이지로부터 extra 받아오기
        var extraId = intent.getStringExtra("id")
        var extraUnivIdx = intent.getStringExtra("univ")
        var extraName = intent.getStringExtra("name")

        edit_profile_edt_id.setText(extraId)
        edit_profile_edt_name.setText(extraName)

        when (extraUnivIdx) {
            "1" -> {
                edit_profile_tv_univ_select.text = "숭실대학교"
                univIdx = 1
            }
            "2" -> {
                edit_profile_tv_univ_select.text = "중앙대학교"
                univIdx = 2
            }
            "3" -> {
                edit_profile_tv_univ_select.text = "서울대학교"
                univIdx = 3
            }
        }

        // 대학교 선택
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

        // 수정완료 클릭
        edit_profile_button_edit.setOnClickListener {
            edit()
        }

        // 이름입력 focused
        edit_profile_edt_name.setOnFocusChangeListener { v, hasFocus ->
            edit_profile_edt_name.isSelected = hasFocus
        }

        // 비밀번호확인입력 focused
        edit_profile_edt_pw_chk.setOnFocusChangeListener { v, hasFocus ->
            edit_profile_edt_pw_chk.isSelected = hasFocus
            // 비밀번호 체크
            edit_profile_edt_pw_chk.addTextChangedListener {

                if (edit_profile_edt_pw_new.text.toString() == edit_profile_edt_pw_chk.text.toString()) {
                    edit_profile_pw_chk_txt2.visibility = View.INVISIBLE
                } else {
                    edit_profile_pw_chk_txt2.visibility = View.VISIBLE
                }

            }
        }
        edit_profile_edt_pw_chk.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(edit_profile_edt_pw_chk.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    fun edit() {

        // 현재 비밀번호 체크
        val pwJsonData = JSONObject()
        pwJsonData.put("user_pw", edit_profile_edt_pw_now.text.toString())

        val body = JsonParser.parseString(pwJsonData.toString()) as JsonObject

        BoosterServiceImpl.service.checkPW(
            body = body
        )
            .enqueue(object : Callback<NoticeData> {
                override fun onFailure(call: Call<NoticeData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(call: Call<NoticeData>, response: Response<NoticeData>) {
                    if (response.body()!!.success) {
                        edit_profile_pw_chk_txt.visibility = View.INVISIBLE
                        if (!edit_profile_edt_name.text.isNullOrBlank()) {
                            requestEdit()
                        } else {
                            Toast.makeText(
                                this@EditProfileActivity,
                                "이름을 입력해주세요",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        edit_profile_pw_chk_txt.visibility = View.VISIBLE
                    }
                }

            })

        // 이름입력 체크

    }

    fun requestEdit() {
        val editJsonData = JSONObject()
        editJsonData.put("user_name", edit_profile_edt_name.text.toString())
        editJsonData.put("user_university", univIdx)

        // 새로운 비밀번호 입력 안 할 경우, 기존의 비밀번호로 요청
        if (edit_profile_edt_pw_new.text.isNullOrBlank()) {
            editJsonData.put("user_pw", edit_profile_edt_pw_now.text.toString())
        } else {
            editJsonData.put("user_pw", edit_profile_edt_pw_new.text.toString())
        }

        val body = JsonParser.parseString(editJsonData.toString()) as JsonObject
        BoosterServiceImpl.service.editMyProfile(
            body = body
        )
            .enqueue(object : Callback<EditProfileData> {
                override fun onFailure(call: Call<EditProfileData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(
                    call: Call<EditProfileData>,
                    response: Response<EditProfileData>
                ) {
                    val message = response.body()!!.message
                    Toast.makeText(this@EditProfileActivity, message, Toast.LENGTH_SHORT)
                        .show()
                    if (response.body()!!.success) finish()
                }
            })
    }
}