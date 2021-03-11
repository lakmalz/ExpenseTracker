package com.inex.expensetracker.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.inex.expensetracker.di.appModule
import com.inex.expensetracker.di.sharedPreferencesModule
import com.inex.expensetracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, viewModelModule, sharedPreferencesModule))
        }
    }

}