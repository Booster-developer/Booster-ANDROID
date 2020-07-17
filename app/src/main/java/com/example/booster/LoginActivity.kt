package com.example.booster

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.booster.data.datasource.model.LoginData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.ui.bottomtap.BottomTabActivity
import com.example.booster.ui.home.HomeActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var isLoggedIn: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        isLoggedIn = MySharedPreferences(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 아이디입력 focused
        login_edt_id.setOnFocusChangeListener { v, hasFocus ->
            login_edt_id.isSelected = hasFocus
        }

        login_edt_pw.setOnFocusChangeListener { v, hasFocus ->
            login_edt_pw.isSelected = hasFocus
        }
      
        login_edt_pw.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(login_edt_pw.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

        // 로그인 request
        login_button_login.onlyOneClickListener {
            login()
        }

        login_tv_goto_join.onlyOneClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val savedId = data?.getStringExtra("id").toString()
                    val savedPw = data?.getStringExtra("password").toString()
                    login_edt_id.setText(savedId)
                    login_edt_pw.setText(savedPw)
                }
            }
        }
    }

    fun login() {
        val loginJsonData = JSONObject()
        loginJsonData.put("user_id", login_edt_id.text.toString())
        loginJsonData.put("user_pw", login_edt_pw.text.toString())

        val body = JsonParser.parseString(loginJsonData.toString()) as JsonObject
        if (login_edt_id.text.isNullOrBlank() || login_edt_pw.text.isNullOrBlank()) {
            Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
        } else {
            BoosterServiceImpl.service.requestLogin(body)
                .enqueue(object : Callback<LoginData> {
                    override fun onFailure(call: Call<LoginData>, t: Throwable) {
                        Log.e("error", t.toString())
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<LoginData>,
                        response: Response<LoginData>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                val intent =
                                    Intent(this@LoginActivity, BottomTabActivity::class.java)
                                intent.putExtra("univ", response.body()!!.data.university_idx)
                                intent.putExtra("token", response.body()!!.data.accessToken)
                                startActivity(intent)

                                isLoggedIn.isLoggedIn = "isLoggedIn"
                                finish()
                            }
                        } else {
                            Log.e("onReponse else", response.toString())
                        }
                    }

                })
        }
    }

//    // 로그인 유지
//    override fun onStart() {
//        super.onStart()
//        if (isLoggedIn.isLoggedIn == "isLoggedIn") {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
}

class MySharedPreferences(context: Context) {

    private val prefsName = "prefs"
    private val prefsKey = "isLoggedIn"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, 0)

    var isLoggedIn: String
        get() = prefs.getString(prefsKey, "").toString()
        set(value) = prefs.edit().putString(prefsKey, value).apply()
}