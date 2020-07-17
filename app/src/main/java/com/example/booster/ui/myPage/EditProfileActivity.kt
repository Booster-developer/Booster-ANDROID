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
    var chkNewPW = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // 뒤로가기 버튼
        act_edit_profile_img_back.setOnClickListener {
            finish()
        }

        // 마이페이지로부터 extra 받아오기
        var extraId = intent.getStringExtra("id")
        var extraUnivIdx = intent.getStringExtra("univ")
        var extraName = intent.getStringExtra("name")

        act_edit_profile_edt_id.setText(extraId)
        act_edit_profile_edt_name.setText(extraName)

        when (extraUnivIdx) {
            "1" -> {
                act_edit_profile_tv_univ_select.text = "숭실대학교"
                univIdx = 1
            }
            "2" -> {
                act_edit_profile_tv_univ_select.text = "중앙대학교"
                univIdx = 2
            }
            "3" -> {
                act_edit_profile_tv_univ_select.text = "서울대학교"
                univIdx = 3
            }
        }

        // 대학교 선택
        act_edit_profile_tv_univ_select_btn.setOnClickListener {
            act_edit_profile_ll_univ.visibility = View.VISIBLE
        }

        act_edit_profile_tv_univ_1.setOnClickListener {
            act_edit_profile_tv_univ_select.text = "숭실대학교"
            univIdx = 1
            act_edit_profile_ll_univ.visibility = View.GONE
        }
        act_edit_profile_tv_univ_2.setOnClickListener {
            act_edit_profile_tv_univ_select.text = "중앙대학교"
            univIdx = 2
            act_edit_profile_ll_univ.visibility = View.GONE
        }
        act_edit_profile_tv_univ_3.setOnClickListener {
            act_edit_profile_tv_univ_select.text = "서울대학교"
            univIdx = 3
            act_edit_profile_ll_univ.visibility = View.GONE
        }

        // 수정완료 클릭
        act_edit_profile_button_edit.setOnClickListener {
            edit()
        }

        // 이름입력 focused
        act_edit_profile_edt_name.setOnFocusChangeListener { v, hasFocus ->
            act_edit_profile_edt_name.isSelected = hasFocus
        }

        // 현재 비밀번호 입력 focused
        act_edit_profile_edt_pw_now.setOnFocusChangeListener { v, hasFocus ->
            act_edit_profile_edt_pw_now.isSelected = hasFocus
        }

        // 새 비밀번호 입력 focused
        act_edit_profile_edt_pw_new.setOnFocusChangeListener { v, hasFocus ->
            act_edit_profile_edt_pw_new.isSelected = hasFocus
        }

        // 비밀번호확인입력 focused
        act_edit_profile_edt_pw_chk.setOnFocusChangeListener { v, hasFocus ->
            act_edit_profile_edt_pw_chk.isSelected = hasFocus
            // 비밀번호 체크
            act_edit_profile_edt_pw_chk.addTextChangedListener {

                if (act_edit_profile_edt_pw_new.text.toString() == act_edit_profile_edt_pw_chk.text.toString()) {
                    act_edit_profile_pw_chk_txt2.visibility = View.INVISIBLE
                    chkNewPW = true
                } else {
                    act_edit_profile_pw_chk_txt2.visibility = View.VISIBLE
                    chkNewPW = false
                }

            }
        }
        act_edit_profile_edt_pw_chk.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(act_edit_profile_edt_pw_chk.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    fun edit() {

        // 현재 비밀번호 체크
        val pwJsonData = JSONObject()
        pwJsonData.put("user_pw", act_edit_profile_edt_pw_now.text.toString())

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
                        act_edit_profile_pw_chk_txt.visibility = View.INVISIBLE
                        if (!act_edit_profile_edt_name.text.isNullOrBlank() && chkNewPW) {
                            requestEdit()
                        } else {
                            Toast.makeText(
                                this@EditProfileActivity,
                                "모든 항목을 확인해주세요",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        act_edit_profile_pw_chk_txt.visibility = View.VISIBLE
                    }
                }

            })
    }

    fun requestEdit() {
        val editJsonData = JSONObject()
        editJsonData.put("user_name", act_edit_profile_edt_name.text.toString())
        editJsonData.put("user_university", univIdx)

        // 새로운 비밀번호 입력 안 할 경우, 기존의 비밀번호로 요청
        if (act_edit_profile_edt_pw_new.text.isNullOrBlank()) {
            editJsonData.put("user_pw", act_edit_profile_edt_pw_now.text.toString())
        } else {
            editJsonData.put("user_pw", act_edit_profile_edt_pw_new.text.toString())
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