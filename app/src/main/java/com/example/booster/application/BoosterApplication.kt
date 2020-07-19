package com.example.booster.application

import android.app.Application
import com.example.booster.ui.user.MySharedPreferences
import com.example.booster.util.UserManager

class BoosterApplication : Application() {

    companion object{
        lateinit var prefs : MySharedPreferences

        lateinit var  globalApplication: BoosterApplication
        lateinit var instance : BoosterApplication

        fun getGlobalApplicationContext(): BoosterApplication {
            return instance
        }
    }

    override fun onCreate() {
        prefs =
            MySharedPreferences(applicationContext)
        super.onCreate()
        instance = this
        globalApplication = this
        UserManager.init(this)
    }

}