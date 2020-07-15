package com.example.booster.ui.myPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.booster.R
import com.example.booster.data.datasource.model.ProfileData
import com.example.booster.data.remote.network.BoosterServiceImpl
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        BoosterServiceImpl.service.getMyProfile(token = "")
            .enqueue(object : Callback<ProfileData> {
                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                    if (response.isSuccessful) {
                        val data = response.body()!!.data
                        mypage_name.text = data.user_name
                        when (data.univ_idx) {
                            1 -> mypage_univ.text = "숭실대학교"
                            2 -> mypage_univ.text = "중앙대학교"
                            3 -> mypage_univ.text = "서울대학교"
                        }
                        mypage_id.text = data.user_id
                        mypage_point.text = data.user_point.toString()
                    }
                }

            })

        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
