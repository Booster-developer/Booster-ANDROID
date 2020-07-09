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

    lateinit var univIdx: String
    lateinit var idChk: String
    lateinit var pwChk: String

    override fun onCreate(savedInstanceState: Bundle?) {

        val requestJoinToServer = RequestJoinToServer
        val checkIdToServer = CheckIdToServer

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        univSelectBtn.setOnClickListener {
            univList.visibility = View.VISIBLE
        }

        univ_1.setOnClickListener {
            univSelected.text = "숭실대학교"
            univIdx = "0"
            univList.visibility = View.GONE
        }
        univ_2.setOnClickListener {
            univSelected.text = "중앙대학교"
            univIdx = "1"
            univList.visibility = View.GONE
        }
        univ_3.setOnClickListener {
            univSelected.text = "서울대학교"
            univIdx = "2"
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
                    id_chk_fail.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<ResponseJoin>,
                    response: Response<ResponseJoin>
                ) {
                    id_chk_fail.visibility = View.INVISIBLE
                    id_chk_success.visibility = View.VISIBLE
                    idChk = "success"
                }

            })
        }

        join_btn.setOnClickListener {
            // 비밀번호 확인
            if (join_pw.text != join_pw_chk.text) {
                join_pw_chk_txt.visibility = View.VISIBLE
            } else {
                pwChk = "success"
            }

            // 회원가입 request
            if (idChk == "success" && pwChk == "success") {
                requestJoinToServer.service.requestJoin(
                    RequestJoin(
                        user_id = join_id.text.toString(),
                        user_name = join_name.text.toString(),
                        user_pw = join_pw.text.toString(),
                        user_university = univIdx.toInt()
                    )
                ).enqueue(object : Callback<ResponseJoin> {
                    override fun onFailure(call: Call<ResponseJoin>, t: Throwable) {
                        Log.e("error", t.toString())
                        Toast.makeText(this@JoinActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }

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
