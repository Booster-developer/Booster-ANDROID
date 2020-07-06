package com.example.booster

import android.app.Application
import com.earlyBuddy.earlybuddy_android.di.remoteDataAppModule
import com.earlyBuddy.earlybuddy_android.di.repositoryAppModule
import com.earlyBuddy.earlybuddy_android.di.viewModelAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BoosterApplication : Application() {

    companion object{
        lateinit var  globalApplication: BoosterApplication
        lateinit var instance : BoosterApplication

        fun getGlobalApplicationContext(): BoosterApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalApplication = this

        startKoin {
            androidContext(this@BoosterApplication)
            modules(listOf(
                remoteDataAppModule,
                repositoryAppModule,
                viewModelAppModule
            ))
        }
    }

}