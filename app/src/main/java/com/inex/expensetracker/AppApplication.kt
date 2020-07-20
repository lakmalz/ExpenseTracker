package com.inex.expensetracker

import android.app.Application
import com.facebook.stetho.Stetho
import com.inex.expensetracker.di.appModule
import com.inex.expensetracker.di.sharedPreferencesModule
import com.inex.expensetracker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@AppApplication)
            modules(listOf(appModule, viewModelModule, sharedPreferencesModule))
        }
    }

}