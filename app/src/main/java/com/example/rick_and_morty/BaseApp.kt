package com.example.rick_and_morty

import android.app.Application
import com.example.rick_and_morty.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BaseApp)
            modules(appModule)
        }
    }

}