package com.example.booster.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booster.R
import com.example.booster.data.datasource.model.EngineData
import com.example.booster.data.datasource.model.EngineListData
import com.example.booster.data.remote.network.BoosterServiceImpl
import kotlinx.android.synthetic.main.activity_myengine.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyengineActivity : AppCompatActivity() {

    lateinit var engineListAdapter: EngineListAdapter
    val datas = mutableListOf<EngineData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myengine)

        engineListAdapter = EngineListAdapter(this)
        act_engine_rv.run {
            adapter = engineListAdapter
            layoutManager =
                LinearLayoutManager(this@MyengineActivity, LinearLayoutManager.VERTICAL, false)
        }

        BoosterServiceImpl.service.getEngineList()
            .enqueue(object : Callback<EngineListData> {
                override fun onFailure(call: Call<EngineListData>, t: Throwable) {
                    Log.e("error", t.toString())
                }

                override fun onResponse(
                    call: Call<EngineListData>,
                    response: Response<EngineListData>
                ) {
                    if (response.body()!!.success) {
                        act_myengine_tv_point.text =
                            response.body()!!.data.engine_point.toString() + "P"
                        response.body()!!.data.engine_list.map {
                            datas.apply {
                                add(
                                    it
                                )
                            }
                            engineListAdapter.datas = datas
                            engineListAdapter.notifyDataSetChanged()
                        }
                    }
                }

            })
    }
}