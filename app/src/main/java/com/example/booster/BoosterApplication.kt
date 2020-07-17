package com.example.booster

import android.app.Application
import com.example.booster.util.UserManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
        instance = this
        globalApplication = this
        UserManager.init(this)
    }

}