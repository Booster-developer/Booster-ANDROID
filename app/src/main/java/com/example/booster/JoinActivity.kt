package com.example.booster

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.booster.data.datasource.model.RequestCheckId
import com.example.booster.data.datasource.model.RequestJoin
import com.example.booster.data.datasource.model.ResponseJoin
import com.example.booster.data.remote.network.CheckIdToServer
import com.example.booster.data.remote.network.RequestJoinToServer
import kotlinx.android.synthetic.main.activity_join.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    private lateinit var pwChk: String
    lateinit var idChk: String

    override fun onCreate(savedInstanceState: Bundle?) {

        val requestJoinToServer = RequestJoinToServer
        val checkIdToServer = CheckIdToServer

        var univIdx = -1

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

        id_chk_btn.setOnClickListener {
            // 아이디 중복 체크
            checkIdToServer.service.requestCheckId(
                RequestCheckId(
                    user_id = join_id.text.toString()
                )
            ).enqueue(object : Callback<ResponseJoin> {
                override fun onFailure(call: Call<ResponseJoin>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseJoin>,
                    response: Response<ResponseJoin>
                ) {
                    Log.e("responseaaaaaaaaaa", response.body().toString())
                    if (response.body()!!.success) {
                        id_chk_fail.visibility = View.INVISIBLE
                        id_chk_success.visibility = View.VISIBLE
                        idChk = "success"
                    } else {
                        id_chk_fail.visibility = View.VISIBLE
                    }
                }
            })
        }

        join_btn.setOnClickListener {
            // 비밀번호 확인
            if (join_pw.text.toString() != join_pw_chk.text.toString()) {
                join_pw_chk_txt.visibility = View.VISIBLE
            } else {
                pwChk = "success"
            }

            // 회원가입 request
            if(join_id.text.isNullOrBlank() || join_pw.text.isNullOrBlank() ||join_name.text.isNullOrBlank()||univSelected.text.isNullOrBlank()){
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if (idChk == "success" && pwChk == "success") {
                requestJoinToServer.service.requestJoin(
                    RequestJoin(
                        user_id = join_id.text.toString(),
                        user_name = join_name.text.toString(),
                        user_pw = join_pw.text.toString(),
                        user_university = univIdx
                    )
                ).enqueue(object : Callback<ResponseJoin> {
                    override fun onFailure(call: Call<ResponseJoin>, t: Throwable) {
                        Toast.makeText(this@JoinActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }

                    // 아이디와 비밀번호 로그인 화면으로 전달
                    override fun onResponse(
                        call: Call<ResponseJoin>,
                        response: Response<ResponseJoin>
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
}
