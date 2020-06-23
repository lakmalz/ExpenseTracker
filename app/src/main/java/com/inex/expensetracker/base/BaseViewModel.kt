package com.inex.expensetracker.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inex.expensetracker.BuildConfig
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val subscription = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    fun printError(e: Throwable) {
        if (BuildConfig.DEBUG)
            Log.e("BaseView", e.message)
    }

    fun Log(msg: String) {
        if (BuildConfig.DEBUG)
            Log.e("BaseView", msg)
    }


}