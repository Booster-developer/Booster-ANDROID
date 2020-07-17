package com.example.booster.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booster.JoinActivity
import com.example.booster.LoginActivity
import com.example.booster.R
import com.example.booster.data.datasource.model.ProfileData
import com.example.booster.data.remote.network.BoosterServiceImpl
import kotlinx.android.synthetic.main.fragment_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageFragment : Fragment() {

    var univIdx = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        BoosterServiceImpl.service.getMyProfile()
            .enqueue(object : Callback<ProfileData> {
                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        mypage_tv_name.text = data.user_name
                        univIdx = data.univ_idx
                        when (data.univ_idx) {
                            1 -> mypage_tv_univ.text = "숭실대학교"
                            2 -> mypage_tv_univ.text = "중앙대학교"
                            3 -> mypage_tv_univ.text = "서울대학교"
                        }
                        mypage_tv_id.text = data.user_id
                        mypage_tv_point.text = data.user_point.toString()
                    }
                }

            })

        // 로그아웃 버튼 클릭 시 로그인 페이지로 이동
        logout_btn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mypage_tv_goto_edit.setOnClickListener {

            val intent = Intent(context, EditProfileActivity::class.java)
            intent.putExtra("id", mypage_tv_id.text.toString())
            intent.putExtra("univ", univIdx.toString())
            intent.putExtra("name", mypage_tv_name.text.toString())
            startActivity(intent)
        }

        mypage_tv_goto_myengine.setOnClickListener {

            val intent = Intent(context, MyengineActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        BoosterServiceImpl.service.getMyProfile()
            .enqueue(object : Callback<ProfileData> {
                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        mypage_tv_name.text = data.user_name
                        univIdx = data.univ_idx
                        when (data.univ_idx) {
                            1 -> mypage_tv_univ.text = "숭실대학교"
                            2 -> mypage_tv_univ.text = "중앙대학교"
                            3 -> mypage_tv_univ.text = "서울대학교"
                        }
                        mypage_tv_id.text = data.user_id
                        mypage_tv_point.text = data.user_point.toString()
                    }
                }

            })
    }
}