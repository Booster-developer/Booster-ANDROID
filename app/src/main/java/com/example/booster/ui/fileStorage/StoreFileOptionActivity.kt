package com.example.booster.ui.fileStorage

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.booster.R
import com.example.booster.data.datasource.model.DefaultData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_store_file_option.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFileOptionActivity : AppCompatActivity(),
    FragmentToActivity {

    var color = ""
    var direction = ""
    var side = ""
    var combine = ""
    var range = ""
    var rangeMin = ""
    var rangeMax = ""
    var num = ""

    val requestToServer = BoosterServiceImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_file_option)

        //초기 default로 설정
        order_option_btn_mono.isSelected = true
        order_option_btn_auto.isSelected = true
        order_option_btn_single.isSelected = true
        order_option_btn_cut_1.isSelected = true
        color = "흑백"
        direction = "자동"
        side = "단면"
        combine = "1"
        range = "전체"
        rangeMin="0"
        rangeMax="0"
        num = "1"

        act_store_file_option_btn_back.setOnClickListener {
            finish()
        }

        order_option_btn_mono.setOnClickListener {
            colorReset()
            order_option_btn_mono.isSelected = !order_option_btn_mono.isSelected
            if(order_option_btn_mono.isSelected) {color = "흑백"}
            else colorReset()
        }

        order_option_btn_color.setOnClickListener {
            colorReset()
            order_option_btn_color.isSelected = !order_option_btn_color.isSelected
            if(order_option_btn_color.isSelected) { color = "컬러" }
            else colorReset()
        }

        order_option_btn_auto.setOnClickListener {
            directionReset()
            order_option_btn_auto.isSelected = !order_option_btn_auto.isSelected
            if(order_option_btn_auto.isSelected) { direction = "자동" }
            else directionReset()
        }

        order_option_btn_height.setOnClickListener {
            directionReset()
            order_option_btn_height.isSelected = !order_option_btn_height.isSelected
            if(order_option_btn_height.isSelected) { direction = "세로" }
            else directionReset()
        }

        order_option_btn_width.setOnClickListener {
            directionReset()
            order_option_btn_width.isSelected = !order_option_btn_width.isSelected
            if(order_option_btn_width.isSelected) { direction = "가로" }
            else directionReset()
        }

        order_option_btn_single.setOnClickListener {
            sideReset()
            order_option_btn_single.isSelected = !order_option_btn_single.isSelected
            if(order_option_btn_single.isSelected){ side = "단면" }
            else sideReset()
        }

        order_option_btn_doublehei1.setOnClickListener {
            sideReset()
            order_option_btn_doublehei1.isSelected = !order_option_btn_doublehei1.isSelected
            if(order_option_btn_doublehei1.isSelected){ side = "양면 - 가로넘김" }
            else sideReset()
        }

        order_option_btn_doublehei2.setOnClickListener {
            sideReset()
            order_option_btn_doublehei2.isSelected = !order_option_btn_doublehei2.isSelected
            if(order_option_btn_doublehei2.isSelected){ side = "양면 - 세로넘김" }
            else sideReset()

        }

        order_option_btn_cut_1.setOnClickListener {
            combineReset()
            order_option_btn_cut_1.isSelected = !order_option_btn_cut_1.isSelected
            if(order_option_btn_cut_1.isSelected) { combine = "1" }
            else combineReset()
        }

        order_option_btn_cut_2.setOnClickListener {
            combineReset()
            order_option_btn_cut_2.isSelected = !order_option_btn_cut_2.isSelected
            if(order_option_btn_cut_2.isSelected) {combine = "2"}
            else combineReset()
        }

        order_option_btn_cut_3.setOnClickListener {
            combineReset()
            order_option_btn_cut_3.isSelected = !order_option_btn_cut_3.isSelected
            if(order_option_btn_cut_3.isSelected) { combine = "3" }
            else combineReset()
        }

        order_option_btn_cut_4.setOnClickListener {
            combineReset()
            order_option_btn_cut_4.isSelected = !order_option_btn_cut_4.isSelected
            if(order_option_btn_cut_4.isSelected) { combine = "4" }
            else combineReset()
        }

        order_option_btn_cut_6.setOnClickListener {
            combineReset()
            order_option_btn_cut_6.isSelected = !order_option_btn_cut_6.isSelected
            if(order_option_btn_cut_6.isSelected) { combine = "6" }
            else combineReset()
        }

        order_option_btn_cut_8.setOnClickListener {
            combineReset()
            order_option_btn_cut_8.isSelected = !order_option_btn_cut_8.isSelected
            if(order_option_btn_cut_8.isSelected) { combine = "8" }
            else combineReset()
        }

        order_option_btn_cut_9.setOnClickListener {
            combineReset()
            order_option_btn_cut_9.isSelected = !order_option_btn_cut_9.isSelected
            if(order_option_btn_cut_9.isSelected) { combine = "9" }
            else combineReset()
        }

        order_option_btn_cut_16.setOnClickListener {
            combineReset()
            order_option_btn_cut_16.isSelected = !order_option_btn_cut_16.isSelected
            if(order_option_btn_cut_16.isSelected) { combine = "16" }
            else combineReset()
        }

        order_option_btn_range.setOnClickListener {
            val fileRangeDialog =
                StoreFileOptionRangeFragment()
            fileRangeDialog.show(
                supportFragmentManager,
                "file option ragne fragment"
            )

        }

        order_option_btn_num.setOnClickListener {
            val fileNumDialog =
                StoreFileOptionNumFragment()
            fileNumDialog.show(
                supportFragmentManager, "file option nmm fragment"
            )
        }

        act_store_file_option_btn_option.setOnClickListener {
            val intent = intent
            Log.e("aa", "${color} ${direction} ${side} ${combine} ${range} ${num}")

            val jsonData = JSONObject()
            jsonData.put("file_color", color)
            jsonData.put("file_direction", direction)
            jsonData.put("file_sided_type", side)
            jsonData.put("file_collect", combine)
            jsonData.put("file_range_start", rangeMin)
            jsonData.put("file_range_end", rangeMax)
            jsonData.put("file_copy_number", num)

            val body = JsonParser.parseString(jsonData.toString()) as JsonObject
            Log.e("body", body.toString())

            requestToServer.service.changeOption(
                1, body
            ).enqueue(object : Callback<DefaultData>{
                override fun onFailure(call: Call<DefaultData>, t: Throwable) {
                    //통신 실패
                    Log.e("onResponse", "통신 실패")
                }

                override fun onResponse(
                    call: Call<DefaultData>,
                    response: Response<DefaultData>
                ) {
                    //통신 성공
                    Log.e("onResponse", response.toString())
                    //인텐트 넘겨주기
                    intent.putExtra("color", color)
                    intent.putExtra("direction", direction)
                    intent.putExtra("side", side)
                    intent.putExtra("combine", combine)
                    if(range=="전체"){
                        intent.putExtra("range", range)
                    }else{
                        intent.putExtra("rangeMin", rangeMin)
                        intent.putExtra("rangeMax", rangeMax)
                    }
                    intent.putExtra("num", num)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            })

        }
    }

    override fun communicateRange(r:String, min:Int, max:Int) {
        range = r
        rangeMin = min.toString()
        rangeMax = max.toString()
        if(range == "전체"){
            act_store_file_option_txt_range.text = range
        }else{
            act_store_file_option_txt_range.text = "${rangeMin} ~ ${rangeMax}p"
        }
        act_store_file_option_txt_range.setTextColor(Color.BLACK)
    }

    override fun communicateNum(s:String) {
        num = s
        act_store_file_option_txt_num.text = "${num}부"
        act_store_file_option_txt_num.setTextColor(Color.BLACK)
    }

    fun colorReset(){
        color = ""
        order_option_btn_mono.isSelected = false
        order_option_btn_color.isSelected = false
    }

    fun directionReset(){
        direction = ""
        order_option_btn_auto.isSelected = false
        order_option_btn_height.isSelected = false
        order_option_btn_width.isSelected = false
    }
    fun sideReset(){
        side = ""
        order_option_btn_single.isSelected = false
        order_option_btn_doublehei1.isSelected = false
        order_option_btn_doublehei2.isSelected = false
    }

    fun combineReset(){
        combine = ""
        order_option_btn_cut_1.isSelected = false
        order_option_btn_cut_2.isSelected = false
        order_option_btn_cut_3.isSelected = false
        order_option_btn_cut_4.isSelected = false
        order_option_btn_cut_6.isSelected = false
        order_option_btn_cut_8.isSelected = false
        order_option_btn_cut_9.isSelected = false
        order_option_btn_cut_16.isSelected = false

    }
}
