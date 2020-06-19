package com.lakmalz.expensetracker

import android.app.Application
import com.facebook.stetho.Stetho

class AppApplication : Application() {

//    lateinit var appComponent: AppComponent

    companion object {
        private lateinit var instance: AppApplication
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Stetho.initializeWithDefaults(this);
        /*appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()*/
    }
}